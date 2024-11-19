package lissa.trading.statisticsService.service.excel;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;

public interface ReportService {

    void generateExcelReport(Pageable pageable, String firstName, String lastName,
                             HttpServletResponse response);
}
