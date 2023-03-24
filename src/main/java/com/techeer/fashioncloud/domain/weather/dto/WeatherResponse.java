package com.techeer.fashioncloud.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class WeatherResponse {
    private Integer sky;
    private Integer temperature;
    private Integer hourRainfall;
    private Integer humidity;
    private Integer rainfallType;
    private Integer windSpeed;
}
