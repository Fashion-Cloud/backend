package com.techeer.fashioncloud.domain.weather.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.external.WeatherApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UltraSrtFcstResponse {
    private Integer skyStatus;

    public static UltraSrtFcstResponse of(WeatherApiResponse weatherApiResponse) {

        JsonNode skyData = weatherApiResponse.getItem().get(0);

        return UltraSrtFcstResponse.builder()
                .skyStatus(Integer.parseInt(skyData.get("fcstValue").asText()))
                .build();
    }
}
