package lissa.trading.statisticsService.repository.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lissa.trading.statisticsService.exception.ExcelCreatingException;
import lissa.trading.statisticsService.exception.UsersNotFoundException;
import lissa.trading.statisticsService.repository.AbstractInitialization;
import lissa.trading.statisticsService.service.excel.user.ReportUserService;
import lissa.trading.statisticsService.service.userReport.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportUserServiceTest extends AbstractInitialization {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ReportUserService reportService;

    @Captor
    private ArgumentCaptor<String> captor;

    @Test
    public void generateExcelReport_success() throws IOException {
        when(userService.getUsersForReport(any(), anyString(), anyString())).thenReturn(userReportDtos);

        ServletOutputStream servletOutputStream = createServletOutputStream();

        when(response.getOutputStream()).thenReturn(servletOutputStream);

        reportService.generateExcelReport(PAGEABLE, "firstName", "lastName", response);

        verify(response).setHeader(eq("Content-Disposition"), captor.capture());
        assertTrue(captor.getValue().contains("attachment; filename*=UTF-8"));
    }

    @Test
    public void generateExcelReport_ThrowsUserNotFoundException_When0Users() {
        when(userService.getUsersForReport(any(), anyString(), anyString())).thenReturn(Collections.emptyList());

        assertThrows(UsersNotFoundException.class, () ->
                reportService.generateExcelReport(PAGEABLE, "firstName", "lastName", response));
    }

    @Test
    public void generateExcelReport_ThrowsExcelCreatingException_WhenNullFields() throws IOException {
        when(userService.getUsersForReport(PAGEABLE, "firstName", "lastName"))
                .thenReturn(createNotFullUserReportDto());

        assertThrows(ExcelCreatingException.class, () ->
                reportService.generateExcelReport(PAGEABLE, "firstName", "lastName", response));
    }
}
