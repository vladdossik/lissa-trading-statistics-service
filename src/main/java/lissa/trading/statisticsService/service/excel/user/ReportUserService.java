package lissa.trading.statisticsService.service.excel.user;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lissa.trading.statisticsService.exception.ExcelCreatingException;
import lissa.trading.statisticsService.model.dto.UserReportDto;
import lissa.trading.statisticsService.service.excel.ReportService;
import lissa.trading.statisticsService.service.userReport.UserService;
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
import java.util.Objects;

import static lissa.trading.statisticsService.utils.UserExportExcelColumns.COLUMNS;
import static lissa.trading.statisticsService.utils.UserExportExcelColumns.FORMATTER;

@Service("excelUserService")
@RequiredArgsConstructor
@Slf4j
public class ReportUserService implements ReportService {

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public void generateExcelReport(HttpServletResponse response) {
        String filename = createEncodedFilename("Отчет по пользователям. Дата: ");
        List<UserReportDto> users = userService.getUsersForReport();

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
                    "attachment; filename*=UTF-8''" + filename);
            workbook.write(outputStream);
            log.info("Successfully generated user report");
        } catch (Exception e) {
            throw new ExcelCreatingException("Error creating excel report " + e.getMessage());
        }
    }

    private String createEncodedFilename(String filename) {
        String filenameWithDate = filename + FORMATTER.format(LocalDateTime.now()) + ".xlsx";
        return URLEncoder.encode(filenameWithDate, StandardCharsets.UTF_8)
                .replace("+", "%20");
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

            if (Objects.isNull(value)) {
                cell.setCellValue("");
            } else {
                if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else {
                    cell.setCellValue(value.toString());
                }
            }
        }
    }
}