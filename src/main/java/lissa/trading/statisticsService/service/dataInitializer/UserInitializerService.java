package lissa.trading.statisticsService.service.dataInitializer;

import lissa.trading.statisticsService.model.User;
import lissa.trading.statisticsService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInitializerService implements DataInitializerService {

    private final UserRepository userRepository;
    private final EasyRandom easyRandom;

    @Override
    public void createData() {
        if (userRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                User user = easyRandom.nextObject(User.class);
                userRepository.save(user);
            }
            log.info("Users data successfully initialized");
        } else {
            log.info("Users data is already initialized");
        }
    }
}
