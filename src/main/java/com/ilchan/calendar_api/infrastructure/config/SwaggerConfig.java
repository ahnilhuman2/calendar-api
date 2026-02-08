package com.ilchan.calendar_api.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI calendarOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Calendar API")
                .version("v1.0.0")
                .description("웹뷰 기반 일정관리 앱을 위한 백엔드 REST API (MVP)")
            )
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Local Development Server")
            ));
    }
}
