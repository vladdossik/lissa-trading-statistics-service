package lissa.trading.statisticsService.controller;

import jakarta.servlet.http.HttpServletResponse;
import lissa.trading.statisticsService.service.excel.ExcelService;
import lissa.trading.statisticsService.service.excel.user.ExcelUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/users/report")
public class UserReportController {

    private final ExcelService excelService;

    public UserReportController(@Qualifier("ExcelUserService") ExcelUserService excelUserService) {
        this.excelService = excelUserService;
    }

    @GetMapping
    public void getUsersReport(HttpServletResponse response) {
        excelService.generateExcelReport(response);
    }
}
