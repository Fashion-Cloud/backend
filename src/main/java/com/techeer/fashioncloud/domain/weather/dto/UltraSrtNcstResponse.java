package com.techeer.fashioncloud.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UltraSrtNcstResponse implements ForecastResponse {

    private Double temperature;
    private Integer hourRainfall;
    private Integer humidity;
    private Integer rainfallType;
    private Double windSpeed;

}
