package lissa.trading.statisticsService.service.excel.user;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static lissa.trading.statisticsService.utils.UserExcelMappingUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelUserService implements ExcelService {

    private final UserReportService userReportService;

    @Override
    public void generateExcelReport(HttpServletResponse response) {
        List<UserReportDto> users = userReportService.getUsersForReport();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users report " + TODAY_DATE);

        createHeaders(sheet);

        int rowCount = 1;
        for (UserReportDto user : users) {
            Row row = sheet.createRow(rowCount++);
            writeUserData(user, row);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition",
                "attachment; filename=users_report " + TODAY_DATE + ".xlsx");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("Error writing excel report to output stream", e);
        } finally {
            try {
                workbook.close();
                log.info("Successfully generated excel report");
            } catch (IOException e) {
                log.error("Error closing workbook", e);
            }
        }
    }

    private void createHeaders(Sheet sheet) {
        Row headersRow = sheet.createRow(0);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        for (int i = 0; i < COLUMN_NAMES.size(); i++) {
            Cell cell = headersRow.createCell(i);
            cell.setCellValue(COLUMN_NAMES.get(i));
            cell.setCellStyle(cellStyle);
        }
        log.info("Successfully generated headers");
    }

    private void writeUserData(UserReportDto user, Row row) {
        Cell cell;
        for (int i = 0; i < GETTERS_LIST.size(); i++) {
            Object value = GETTERS_LIST.get(i).apply(user);
            cell = row.createCell(i);

            if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            } else if (value instanceof UUID) {
                cell.setCellValue((value.toString()));
            }
        }
        log.info("Successfully generated user data");
    }
}
