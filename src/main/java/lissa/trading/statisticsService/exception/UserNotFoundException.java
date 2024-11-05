package lissa.trading.statisticsService.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotFoundException extends RuntimeException {

    private UUID externalId;

    public UserNotFoundException(String message, UUID externalId) {
        super(message);
        this.externalId = externalId;
    }
}
