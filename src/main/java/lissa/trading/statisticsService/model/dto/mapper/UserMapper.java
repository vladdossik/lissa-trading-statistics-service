package lissa.trading.statisticsService.model.dto.mapper;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.model.dto.UserReportDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReportDto toUserReportDto(User user);
}
