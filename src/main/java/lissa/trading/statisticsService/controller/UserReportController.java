package lissa.trading.statisticsService.controller;

import jakarta.servlet.http.HttpServletResponse;
import lissa.trading.statisticsService.service.excel.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/report")
public class UserReportController {

    private final ExcelService excelService;

    @GetMapping
    public void getUsersReport(HttpServletResponse response) throws IOException {
        excelService.generateExcelReport(response);
    }
}
