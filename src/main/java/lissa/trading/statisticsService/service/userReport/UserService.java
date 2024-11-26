package lissa.trading.statisticsService.service.userReport;

import lissa.trading.statisticsService.dto.UserReportDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserReportDto> getUsersForReport(Pageable pageable, String firstName, String lastName);

    void processUsers(List<UserReportDto> users);
}
