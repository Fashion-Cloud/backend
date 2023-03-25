package com.techeer.fashioncloud.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VilageFcstResponse  implements ForecastResponse {
    private Integer lowestTemp;
    private Integer HighestTemp;
}
