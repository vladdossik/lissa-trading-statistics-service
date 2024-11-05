package lissa.trading.statisticsService.model.dto.mapper;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import lissa.trading.statisticsService.model.dto.UserReportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReportDto toUserReportDto(User user, UserJson userJson);

    UserJson toUserJson(UserReportDto user);

    User createUser(UserReportDto user, UserJson userJson);

    @Mapping(source = "userJson", target = "userJson")
    User updateUser(UserJson userJson, User existingUser);
}
