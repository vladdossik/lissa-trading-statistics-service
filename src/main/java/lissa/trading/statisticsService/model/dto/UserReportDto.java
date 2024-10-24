package lissa.trading.statisticsService.model.dto;

import lombok.Builder;
import lombok.Data;

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
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private BigDecimal percentageChangeSinceYesterday;
    private BigDecimal monetaryChangeSinceYesterday;
    private Integer accountCount;
    private BigDecimal totalBalance;
    private Boolean premStatus;
}
