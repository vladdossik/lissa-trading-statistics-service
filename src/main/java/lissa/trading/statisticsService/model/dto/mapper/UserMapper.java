package lissa.trading.statisticsService.model.dto.mapper;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.dto.UserReportDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReportDto toUserReportDto(User user);

    @AfterMapping
    default void toUserReportDto(User user, @MappingTarget UserReportDto userReportDto) {
        userReportDto.setFirstName(user.getUserJson().getFirstName());
        userReportDto.setLastName(user.getUserJson().getLastName());
        userReportDto.setTelegramNickname(user.getUserJson().getTelegramNickname());
        userReportDto.setCreatedAt(user.getUserJson().getCreatedAt());
        userReportDto.setUpdatedAt(user.getUserJson().getUpdatedAt());
        userReportDto.setAccountCount(user.getUserJson().getAccountCount());
        userReportDto.setPercentageChangeSinceYesterday(user.getUserJson().getPercentageChangeSinceYesterday());
        userReportDto.setMonetaryChangeSinceYesterday(user.getUserJson().getMonetaryChangeSinceYesterday());
        userReportDto.setTotalBalance(user.getUserJson().getTotalBalance());
    }
}
