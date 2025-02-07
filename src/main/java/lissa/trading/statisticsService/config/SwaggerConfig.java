package lissa.trading.statisticsService.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API для сервиса статистики трейдинг ассистента")
                        .description("Этот API предоставляет методы для генерации статистики о пользователях, акциях" +
                                "в формате .xlsx и .csv")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Александр")
                                .url("https://t.me/s1lweak")))
                .components(new Components()
                                    .addSecuritySchemes("bearer-key", new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("analytics")
                .pathsToMatch("/v1/reports/**")
                .addOpenApiCustomizer(openApi -> openApi
                        .addSecurityItem(new SecurityRequirement().addList("bearer-key")))
                .build();
    }
}
