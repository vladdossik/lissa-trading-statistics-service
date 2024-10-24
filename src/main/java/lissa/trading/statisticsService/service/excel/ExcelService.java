package lissa.trading.statisticsService.service.excel;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ExcelService {

    void generateExcelReport(HttpServletResponse response) throws IOException;
}
