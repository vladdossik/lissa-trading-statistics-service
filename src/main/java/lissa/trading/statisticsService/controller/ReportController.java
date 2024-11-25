package lissa.trading.statisticsService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lissa.trading.statisticsService.service.excel.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/reports")
@RequiredArgsConstructor
public class ReportController {

    @Qualifier("excelUserService")
    private final ReportService userReportService;

    @Operation(summary = "Сгенерировать отчет по пользователям c фильтрацией и пагинацией в формате .xlsx")
    @ApiResponse(responseCode = "200",
            description = "Отчет успешно сгенерирован"
    )

    @GetMapping("user")
    public void getUsersReport(Pageable pageable, @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               HttpServletResponse response) {
        userReportService.generateExcelReport(pageable, firstName, lastName, response);
    }
}
