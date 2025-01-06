package lissa.trading.statisticsService.config;

import org.jeasy.random.EasyRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class EasyRandomConfig {

    @Bean
    public EasyRandom easyRandom() {
        return new EasyRandom();
    }
}
