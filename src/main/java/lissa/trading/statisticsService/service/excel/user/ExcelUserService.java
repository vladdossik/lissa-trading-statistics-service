package lissa.trading.statisticsService.service.excel.user;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lissa.trading.statisticsService.exception.ExcelCreatingException;
import lissa.trading.statisticsService.model.dto.UserReportDto;
import lissa.trading.statisticsService.service.excel.ExcelService;
import lissa.trading.statisticsService.service.userReport.UserReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static lissa.trading.statisticsService.utils.UserExportExcelColumns.COLUMNS;
import static lissa.trading.statisticsService.utils.UserExportExcelColumns.FORMATTER;

@Service("ExcelUserService")
@RequiredArgsConstructor
@Slf4j
public class ExcelUserService implements ExcelService {

    private final UserReportService userReportService;

    @Override
    @Transactional(readOnly = true)
    public void generateExcelReport(HttpServletResponse response) {
        String filename = "Отчет по пользователям. Дата: "
                + FORMATTER.format(LocalDateTime.now()) + ".xlsx";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                .replace("+", "%20");
        List<UserReportDto> users = userReportService.getUsersForReport();

        try (Workbook workbook = new SXSSFWorkbook();
             ServletOutputStream outputStream = response.getOutputStream()) {

            Sheet sheet = workbook.createSheet("Отчет по пользователям");
            createHeaders(sheet);

            for (int i = 0; i < users.size(); i++) {
                Row row = sheet.createRow(i + 1);
                writeUserData(users.get(i), row);
            }
            log.info("Successfully generated user data");

            response.setHeader("Content-Disposition",
                    "attachment; filename*=UTF-8''" + encodedFilename);
            workbook.write(outputStream);
            log.info("Successfully generated user report");
        } catch (Exception e) {
            throw new ExcelCreatingException("Error creating excel report");
        }
    }

    private void createHeaders(Sheet sheet) {
        Row headersRow = sheet.createRow(0);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        for (int i = 0; i < COLUMNS.size(); i++) {
            Cell cell = headersRow.createCell(i);
            cell.setCellValue(COLUMNS.get(i).getColumnName());
            cell.setCellStyle(cellStyle);
        }
        log.info("Successfully generated headers");
    }

    private void writeUserData(UserReportDto user, Row row) {
        Cell cell;
        for (int i = 0; i < COLUMNS.size(); i++) {
            Object value = COLUMNS.get(i).getColumnValue(user);
            cell = row.createCell(i);

            if (value == null) {
                cell.setCellValue("");
            } else {
                switch (value) {
                    case Integer integer -> cell.setCellValue(integer);
                    case Double d -> cell.setCellValue(d);
                    case Boolean b -> cell.setCellValue(b);
                    default -> cell.setCellValue(value.toString());
                }
            }
        }
    }
}
