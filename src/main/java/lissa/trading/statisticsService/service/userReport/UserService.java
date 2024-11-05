package lissa.trading.statisticsService.service.userReport;

import lissa.trading.statisticsService.model.dto.UserReportDto;

import java.util.List;

public interface UserService {

    List<UserReportDto> getUsersForReport();

    void processUsers(List<UserReportDto> users);
}
