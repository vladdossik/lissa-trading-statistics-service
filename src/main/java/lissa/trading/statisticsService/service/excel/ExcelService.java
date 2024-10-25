package lissa.trading.statisticsService.service.excel;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelService {

    void generateExcelReport(HttpServletResponse response);
}
