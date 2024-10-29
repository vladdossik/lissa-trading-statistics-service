package lissa.trading.statisticsService.service.excel;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

    void generateExcelReport(HttpServletResponse response);
}
