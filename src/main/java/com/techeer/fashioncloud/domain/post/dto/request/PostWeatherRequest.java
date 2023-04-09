package com.techeer.fashioncloud.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class PostWeatherRequest {
    private Integer skyCode;
    private Integer rainfallCode;
    private Double windChill;
}
