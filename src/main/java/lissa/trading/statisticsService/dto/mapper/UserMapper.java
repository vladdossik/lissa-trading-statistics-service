package lissa.trading.statisticsService.dto.mapper;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.UserJson;
import lissa.trading.statisticsService.dto.UserReportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "externalId", source = "externalId")
    @Mapping(target = "firstName", source = "userJson.firstName")
    @Mapping(target = "lastName", source = "userJson.lastName")
    @Mapping(target = "telegramNickname", source = "userJson.telegramNickname")
    @Mapping(target = "percentageChangeSinceYesterday", source = "userJson.percentageChangeSinceYesterday")
    @Mapping(target = "monetaryChangeSinceYesterday", source = "userJson.monetaryChangeSinceYesterday")
    @Mapping(target = "accountCount", source = "userJson.accountCount")
    @Mapping(target = "totalBalance", source = "userJson.totalBalance")
    @Mapping(target = "createdAt", source = "userJson.createdAt")
    @Mapping(target = "updatedAt", source = "userJson.updatedAt")
    UserReportDto toUserReportDto(User user);

    UserJson toUserJson(UserReportDto user);

    User createUser(UserReportDto user, UserJson userJson);

    @Mapping(source = "userJson", target = "userJson")
    User updateUser(UserJson userJson, User existingUser);
}
