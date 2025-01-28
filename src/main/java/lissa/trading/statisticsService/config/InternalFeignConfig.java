package lissa.trading.statisticsService.config;

import feign.RequestInterceptor;
import lissa.trading.statisticsService.client.user.feign.InternalTokenFeignInterceptor;
import lissa.trading.statisticsService.security.internal.InternalTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class InternalFeignConfig {

    private final InternalTokenService tokenService;

    @Bean
    public RequestInterceptor internalTokenInterceptor() {
        return new InternalTokenFeignInterceptor(tokenService);
    }
}
