package com.techeer.fashioncloud.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GeoConfig {

    @Value("${openapi.geocoding.key}")
    private String key;

}
