package com.techeer.fashioncloud.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
public class WeatherConfig {
    @Value("${openapi.weather.key.encoding}")
    private String encodingKey;

    @Value("${openapi.weather.key.decoding}")
    private String decodingKey;
}
