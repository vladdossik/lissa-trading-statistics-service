package lissa.trading.statisticsService.mapper;

import lissa.trading.statisticsService.dto.UserReportDto;
import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import org.junit.jupiter.api.BeforeAll;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Random;
import java.util.UUID;

public abstract class AbstractMapperInitialization {

    protected static User user;
    protected static UserReportDto userReportDto;
    protected static UserJson userJson;
    private static final Random RANDOM = new Random();
    private static final int MAX = 999;

    protected static UserMapper userMapper;

    @BeforeAll
    static void setup() {
        userMapper = new UserMapperImpl();
        user = createFullUser();
        userReportDto = createFullUserReportDto();
        userJson = createUserJson();
    }

    private static User createFullUser() {
        int randomInt = RANDOM.nextInt(MAX);
        return User.builder()
                .externalId(UUID.randomUUID())
                .userJson(UserJson.builder()
                        .firstName("firstName" + randomInt)
                        .lastName("lastName" + randomInt)
                        .accountCount(randomInt)
                        .createdAt(OffsetDateTime.now().minusHours(randomInt))
                        .updatedAt(OffsetDateTime.now().minusHours(randomInt))
                        .telegramNickname("tgnickname" + randomInt)
                        .totalBalance(BigDecimal.valueOf(randomInt))
                        .percentageChangeSinceYesterday(BigDecimal.valueOf(randomInt))
                        .monetaryChangeSinceYesterday(BigDecimal.valueOf(randomInt))
                        .build())
                .build();
    }

    private static UserReportDto createFullUserReportDto() {
        int randomInt = RANDOM.nextInt(MAX);
        return UserReportDto.builder()
                .externalId(UUID.randomUUID())
                .firstName("firstName" + randomInt)
                .lastName("lastName" + randomInt)
                .accountCount(randomInt)
                .createdAt(OffsetDateTime.now().minusHours(randomInt))
                .updatedAt(OffsetDateTime.now().minusHours(randomInt))
                .telegramNickname("tgnickname" + randomInt)
                .totalBalance(BigDecimal.valueOf(randomInt))
                .percentageChangeSinceYesterday(BigDecimal.valueOf(randomInt))
                .monetaryChangeSinceYesterday(BigDecimal.valueOf(randomInt))
                .build();
    }

    private static UserJson createUserJson() {
        int randomInt = RANDOM.nextInt(MAX);
        return UserJson.builder()
                .firstName("firstName" + randomInt)
                .lastName("lastName" + randomInt)
                .accountCount(randomInt)
                .createdAt(OffsetDateTime.now().minusHours(randomInt))
                .updatedAt(OffsetDateTime.now().minusHours(randomInt))
                .telegramNickname("tgnickname" + randomInt)
                .totalBalance(BigDecimal.valueOf(randomInt))
                .percentageChangeSinceYesterday(BigDecimal.valueOf(randomInt))
                .monetaryChangeSinceYesterday(BigDecimal.valueOf(randomInt))
                .build();
    }
}
