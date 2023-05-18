package com.techeer.fashioncloud.domain.post.dto.request;

import com.techeer.fashioncloud.domain.weather.constant.RainfallType;
import com.techeer.fashioncloud.domain.weather.constant.SkyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherGetRequestDto {

    @NotNull
    private SkyStatus skyCode;

    @NotNull
    private RainfallType rainfallCode;

    @NotNull
    private Double windChill;
}