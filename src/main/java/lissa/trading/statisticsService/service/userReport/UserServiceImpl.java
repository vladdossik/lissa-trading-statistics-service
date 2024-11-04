package lissa.trading.statisticsService.service.userReport;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import lissa.trading.statisticsService.model.dto.UserReportDto;
import lissa.trading.statisticsService.model.dto.mapper.UserMapper;
import lissa.trading.statisticsService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    @Transactional(readOnly = true)
    public List<UserReportDto> getUsersForReport() {
        log.info("Getting users for report");
        return userRepository.findAll().stream()
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
                    updateUser(user, existingUser) : createUser(user);
            usersToSave.add(userToSave);
        }

        userRepository.saveAll(usersToSave);
        log.info("Successfully received and saved {} users", usersToSave.size());
    }

    private User updateUser(UserReportDto user, User existingUser) {
        existingUser.getUserJson().setFirstName(user.getFirstName());
        existingUser.getUserJson().setLastName(user.getLastName());
        existingUser.getUserJson().setTelegramNickname(user.getTelegramNickname());
        return existingUser;
    }

    private User createUser(UserReportDto user) {
        UserJson userJson = UserJson.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .telegramNickname(user.getTelegramNickname())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .percentageChangeSinceYesterday(user.getPercentageChangeSinceYesterday())
                .monetaryChangeSinceYesterday(user.getMonetaryChangeSinceYesterday())
                .accountCount(user.getAccountCount())
                .totalBalance(user.getTotalBalance())
                .build();

        return User.builder()
                .externalId(user.getExternalId())
                .userJson(userJson)
                .build();
    }
}