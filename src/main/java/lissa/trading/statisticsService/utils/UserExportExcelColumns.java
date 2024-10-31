package lissa.trading.statisticsService.utils;

import lissa.trading.statisticsService.model.dto.UserReportDto;
import lissa.trading.statisticsService.utils.columns.ExportColumn;
import lissa.trading.statisticsService.utils.columns.NumericColumn;
import lissa.trading.statisticsService.utils.columns.TextColumn;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserExportExcelColumns {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm.ss");

    public static final List<ExportColumn<UserReportDto, ?>> COLUMNS = List.of(
            new TextColumn<>("Внешний идентификатор", UserReportDto::getExternalId),
            new TextColumn<>("Имя пользователя", UserReportDto::getFirstName),
            new TextColumn<>("Фамилия пользователя", UserReportDto::getLastName),
            new TextColumn<>("Ник в телеграм", UserReportDto::getTelegramNickname),
            new TextColumn<>("Дата создания", UserReportDto::getCreatedAt),
            new TextColumn<>("Дата обновления", UserReportDto::getUpdatedAt),
            new NumericColumn<>("Процент изменения со вчерашнего дня",
                    dto -> dto.getPercentageChangeSinceYesterday().doubleValue()),
            new NumericColumn<>("Изменение в деньгах со вчерашнего дня",
                    dto -> dto.getMonetaryChangeSinceYesterday().doubleValue()),
            new NumericColumn<>("Количество аккаунтов", UserReportDto::getAccountCount),
            new NumericColumn<>("Общий баланс", dto -> dto.getTotalBalance().doubleValue())
    );
}
