package lissa.trading.statisticsService.repository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import lissa.trading.statisticsService.dto.UserReportDto;
import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class AbstractInitialization {

    protected static List<UserReportDto> userReportDtos = new ArrayList<>();
    protected static List<User> users = new ArrayList<>();
    protected static List<UUID> uuids = new ArrayList<>();
    protected static final Pageable PAGEABLE = Pageable.unpaged();
    protected List<User> existingUsers = new ArrayList<>();
    private static final Random RANDOM = new Random();
    private static final int MAX = 999;

    @BeforeAll
    public static void beforeAll() {
        for (int i = 0; i < 4; i++) {
            createFullUserReportDto();
            createUsers();
            uuids.add(userReportDtos.get(i).getExternalId());
        }
    }

    protected ServletOutputStream createServletOutputStream() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        return new ServletOutputStream() {
            @Override
            public void write(int b) {
                byteArrayOutputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };
    }

    protected List<UserReportDto> createNotFullUserReportDto() {
        return List.of(UserReportDto.builder()
                .externalId(UUID.randomUUID())
                .firstName("firstName" + RANDOM.nextInt(MAX))
                .lastName("lastName" + RANDOM.nextInt(MAX))
                .build());
    }

    private static void createFullUserReportDto() {
        int randomInt = RANDOM.nextInt(MAX);
        userReportDtos.add(UserReportDto.builder()
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
                .build());
    }

    private static void createUsers() {
        int randomInt = RANDOM.nextInt(MAX);
        users.add(User.builder()
                .externalId(UUID.randomUUID())
                .userJson(UserJson.builder()
                        .firstName("firstName" + randomInt)
                        .lastName("lastName" + randomInt)
                        .build())
                .build());
    }
}