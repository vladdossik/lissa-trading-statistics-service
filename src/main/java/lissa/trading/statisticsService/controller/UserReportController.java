package lissa.trading.statisticsService.controller;

import jakarta.servlet.http.HttpServletResponse;
import lissa.trading.statisticsService.service.excel.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class UserReportController {

    @Qualifier("ExcelUserService")
    private final ReportService reportService;

    @GetMapping("users/report")
    public void getUsersReport(HttpServletResponse response) {
        reportService.generateExcelReport(response);
    }
}
