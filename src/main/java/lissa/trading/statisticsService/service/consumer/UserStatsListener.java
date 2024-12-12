package lissa.trading.statisticsService.service.consumer;

import lissa.trading.statisticsService.dto.UserReportDto;
import lissa.trading.statisticsService.service.userReport.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStatsListener implements StatsListener<UserReportDto> {

    private final UserService userService;

    @RabbitListener(queues = "${integration.rabbit.inbound.user-service.queue}")
    @Transactional
    public void receiveUsersData(List<UserReportDto> users) {
        if (CollectionUtils.isEmpty(users)) {
            log.info("List of users is empty");
            return;
        }

        userService.processUsers(users);
    }
}