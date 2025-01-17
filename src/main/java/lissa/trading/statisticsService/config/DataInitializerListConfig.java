package lissa.trading.statisticsService.config;

import lissa.trading.statisticsService.service.dataInitializer.DataInitializerService;
import lissa.trading.statisticsService.service.dataInitializer.UserInitializerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("local")
public class DataInitializerListConfig {

    private final UserInitializerService userInitializerService;

    @Bean
    public List<DataInitializerService> dataInitializers() {
        return List.of(userInitializerService);
    }
}
