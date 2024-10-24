package lissa.trading.statisticsService.utils;

import lissa.trading.statisticsService.model.dto.UserReportDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

@Component
public class UserExcelMappingUtil {

    public static final List<String> COLUMN_NAMES = List.of(
            "Внешний идентификатор",
            "Имя",
            "Фамилия",
            "Ник в Telegram",
            "Дата создания",
            "Дата обновления",
            "Процент изменения с вчерашнего дня",
            "Изменение в деньгах с вчерашнего дня",
            "Количество аккаунтов",
            "Общий баланс",
            "Премиум статус"
    );
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm");
    public static final String TODAY_DATE = FORMATTER.format(LocalDateTime.now());
    public static final List<Function<UserReportDto, Object>> GETTERS_LIST = List.of(
            UserReportDto::getExternalId,
            UserReportDto::getFirstName,
            UserReportDto::getLastName,
            UserReportDto::getTelegramNickname,
            dto -> dto.getCreatedAt().format(FORMATTER),
            dto -> dto.getUpdatedAt().format(FORMATTER),
            dto -> dto.getPercentageChangeSinceYesterday().doubleValue(),
            dto -> dto.getMonetaryChangeSinceYesterday().doubleValue(),
            UserReportDto::getAccountCount,
            dto -> dto.getTotalBalance().doubleValue(),
            UserReportDto::getPremStatus
    );
}
