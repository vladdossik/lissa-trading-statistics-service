package lissa.trading.statisticsService.dto.mapper;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import lissa.trading.statisticsService.dto.UserReportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "externalId", target = "externalId")
    @Mapping(source = "userJson.firstName", target = "firstName")
    @Mapping(source = "userJson.lastName", target = "lastName")
    @Mapping(source = "userJson.telegramNickname", target = "telegramNickname")
    @Mapping(source = "userJson.percentageChangeSinceYesterday", target = "percentageChangeSinceYesterday")
    @Mapping(source = "userJson.monetaryChangeSinceYesterday", target = "monetaryChangeSinceYesterday")
    @Mapping(source = "userJson.accountCount", target = "accountCount")
    @Mapping(source = "userJson.totalBalance", target = "totalBalance")
    @Mapping(source = "userJson.createdAt", target = "createdAt")
    @Mapping(source = "userJson.updatedAt", target = "updatedAt")
    UserReportDto toUserReportDto(User user);

    UserJson toUserJson(UserReportDto user);

    User createUser(UserReportDto user, UserJson userJson);

    @Mapping(source = "userJson", target = "userJson")
    User updateUser(UserJson userJson, User existingUser);
}
