package com.example.demo.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("App SmartMarket")
                .version("1.1.1")
                .description("Aplicación para la gestión de ventas de un supermercado"));
    }
}
