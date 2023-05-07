package com.techeer.fashioncloud.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostWeatherRequest {
    private Integer skyCode;
    private Integer rainfallCode;
    private Double windChill;
}
