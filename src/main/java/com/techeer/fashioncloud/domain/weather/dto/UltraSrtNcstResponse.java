package com.techeer.fashioncloud.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UltraSrtNcstResponse {

    private Double temperature;
    private Double hourRainfall;
    private Integer humidity;
    private Integer rainfallType;
    private Double windSpeed;

}
