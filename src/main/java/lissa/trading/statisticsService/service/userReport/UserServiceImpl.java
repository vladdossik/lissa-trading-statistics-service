package lissa.trading.statisticsService.service.userReport;

import lissa.trading.statisticsService.client.user.feign.UserServiceClient;
import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.dto.UserReportDto;
import lissa.trading.statisticsService.dto.mapper.UserMapper;
import lissa.trading.statisticsService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserServiceClient userServiceClient;

    @Override
    @Transactional(readOnly = true)
    public List<UserReportDto> getUsersForReport(Pageable pageable, String firstName, String lastName) {
        log.info("Getting users for report with pagination and filters: {}, {}, {}", pageable, firstName, lastName);
        List<UUID> externalIds = getExternalIdsForReport(pageable, firstName, lastName);
        log.info("Received: {} ids", externalIds.size());

        return userRepository.findAllByExternalIdIn(externalIds)
                .stream()
                .map(user -> userMapper.toUserReportDto(user, user.getUserJson()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void processUsers(List<UserReportDto> users) {
        List<UUID> externalIds = users.stream().map(UserReportDto::getExternalId)
                .toList();

        List<User> existingUsers = userRepository.findAllByExternalIdIn(externalIds);

        Map<UUID, User> existingUserMap = existingUsers.stream()
                .collect(Collectors.toMap(User::getExternalId, user -> user));

        List<User> usersToSave = new ArrayList<>();
        for (UserReportDto user : users) {
            User existingUser = existingUserMap.get(user.getExternalId());
            User userToSave = existingUser != null ?
                    userMapper.updateUser(userMapper.toUserJson(user), existingUser) :
                    userMapper.createUser(user, userMapper.toUserJson(user));
            usersToSave.add(userToSave);
        }

        userRepository.saveAll(usersToSave);
        log.info("Successfully received and saved {} users", usersToSave.size());
    }

    private List<UUID> getExternalIdsForReport(Pageable pageable, String firstName, String lastName) {
        return userServiceClient.getUserIdsWithPaginationAndFilters(pageable, firstName, lastName)
                .getContent()
                .stream()
                .flatMap(userIdsResponseDto -> userIdsResponseDto.getExternalIds().stream())
                .toList();
    }
}