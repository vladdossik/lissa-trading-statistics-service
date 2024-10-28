package lissa.trading.statisticsService.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class UserReportDto {
    private UUID externalId;
    private String firstName;
    private String lastName;
    private String telegramNickname;
    private BigDecimal percentageChangeSinceYesterday;
    private BigDecimal monetaryChangeSinceYesterday;
    private Integer accountCount;
    private BigDecimal totalBalance;
    private Boolean premStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime updatedAt;
}
