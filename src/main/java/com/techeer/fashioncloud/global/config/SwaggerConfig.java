package com.techeer.fashioncloud.global.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI reviewOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("FashionCloud API")
                .description("API for FashionCloud Service"));
    }

}