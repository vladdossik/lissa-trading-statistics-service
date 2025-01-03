package lissa.trading.statisticsService.config;

import org.jeasy.random.EasyRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EasyRandomConfig {

    @Bean
    public EasyRandom easyRandom() {
        return new EasyRandom();
    }
}
