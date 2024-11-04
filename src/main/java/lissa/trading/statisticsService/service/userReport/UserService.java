package lissa.trading.statisticsService.service.userReport;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.dto.UserReportDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    List<UserReportDto> getUsersForReport();

    Map<UUID, User> processUsers(List<UserReportDto> users);
}
