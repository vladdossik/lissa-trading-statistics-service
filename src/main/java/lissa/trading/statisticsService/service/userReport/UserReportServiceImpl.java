package lissa.trading.statisticsService.service.userReport;

import lissa.trading.statisticsService.model.dto.UserReportDto;
import lissa.trading.statisticsService.model.dto.mapper.UserMapper;
import lissa.trading.statisticsService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserReportServiceImpl implements UserReportService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserReportDto> getUsersForReport() {
        log.info("Getting users for report");
        return userRepository.findAll().stream()
                .map(user -> userMapper.toUserReportDto(user, user.getUserJson()))
                .toList();
    }
}
