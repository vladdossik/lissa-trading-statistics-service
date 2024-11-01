package lissa.trading.statisticsService.service.consumer;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import lissa.trading.statisticsService.model.dto.UserReportDto;
import lissa.trading.statisticsService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStatsConsumer implements StatsConsumer<UserReportDto> {

    private final UserRepository userRepository;

    @RabbitListener(queues = "${integration.rabbit.user-service.user-queue}")
    @Transactional
    public void receiveUsersData(List<UserReportDto> users) {
        List<User> usersToSave = new ArrayList<>();
        for (UserReportDto user : users) {
            User existingUser = userRepository.findByExternalId(user.getExternalId());
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