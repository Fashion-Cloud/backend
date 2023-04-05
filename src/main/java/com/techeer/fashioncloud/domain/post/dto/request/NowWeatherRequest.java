package com.techeer.fashioncloud.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class NowWeatherRequest {
    private Integer sky;
    private Double temperatue;
    private Double humidity;
    private Integer rainfallType;
    private Double windSpeed;
}
