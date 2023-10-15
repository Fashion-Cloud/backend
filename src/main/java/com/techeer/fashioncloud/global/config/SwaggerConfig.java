package com.techeer.fashioncloud.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("FashionCloud API")
                .description("API for FashionCloud Service");

        String jwtSchemeName = "Bearer JWT Authentication";

        // 요청 헤더에 인증 정보 포함
        SecurityRequirement securityItem = new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityItem)
                .components(components);
    }
}