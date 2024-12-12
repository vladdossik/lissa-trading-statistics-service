package lissa.trading.statisticsService.mapper;

import lissa.trading.statisticsService.dto.UserReportDto;
import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest extends AbstractMapperInitialization {

    @Test
    public void toUserReportDto_Success() {
        UserReportDto result = userMapper.toUserReportDto(user);
        assertNotNull(result);
        assertEquals(user.getExternalId(), result.getExternalId());
        assertEquals(user.getUserJson().getTotalBalance(), result.getTotalBalance());
        assertEquals(user.getUserJson().getTelegramNickname(), result.getTelegramNickname());
    }

    @Test
    public void toUserJson_Success() {
        UserJson result = userMapper.toUserJson(userReportDto);
        assertNotNull(result);
        assertEquals(userReportDto.getFirstName(), result.getFirstName());
        assertEquals(userReportDto.getTotalBalance(), result.getTotalBalance());
    }

    @Test
    public void createUser_Success() {
        User result = userMapper.createUser(userReportDto, userJson);
        assertNotNull(result);
        assertEquals(userReportDto.getExternalId(), result.getExternalId());
        assertEquals(userJson.getTelegramNickname(), result.getUserJson().getTelegramNickname());
        assertEquals(userJson.getTotalBalance(), result.getUserJson().getTotalBalance());
    }

    @Test
    public void updateUser_Success() {
        User result = userMapper.updateUser(userJson, user);
        assertNotNull(result);
        assertEquals(user.getExternalId(), result.getExternalId());
        assertEquals(userJson.getTotalBalance(), result.getUserJson().getTotalBalance());
        assertEquals(userJson.getTelegramNickname(), result.getUserJson().getTelegramNickname());
    }
}
