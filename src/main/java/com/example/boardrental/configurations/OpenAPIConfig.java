package com.example.boardrental.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info().title("API Аренда бордов")
                .version("1.0.0")
                .description("API По аренде спортивных бордов");

        return new OpenAPI()
                .info(info)
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080").description("")
                        )
                );
    }
}
