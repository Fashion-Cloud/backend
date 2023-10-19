package com.techeer.fashioncloud.domain.weather.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.external.WeatherApiResponse;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Slf4j
public class UltraSrtNcstResponse {

    private Double temperature;
    private Double hourRainfall;
    private Integer humidity;
    private RainfallType rainfallType;
    private Double windSpeed;

    public static UltraSrtNcstResponse of(WeatherApiResponse weatherApiResponse) {

        JsonNode weatherJsonData = weatherApiResponse.getItem();
        Map<String, String> weatherData = new HashMap<>();

        if (weatherJsonData.isArray()) {
            for (JsonNode data : weatherJsonData) {
                weatherData.put(data.get("category").asText(), data.get("obsrValue").asText());
            }
        }

        return UltraSrtNcstResponse.builder()
                .temperature(Double.parseDouble(weatherData.get("T1H")))
                .hourRainfall(Double.parseDouble(weatherData.get("RN1")))
                .humidity(Integer.parseInt(weatherData.get("REH")))
                .rainfallType(RainfallType.findOf(Integer.parseInt(weatherData.get("PTY"))))
                .windSpeed(Double.parseDouble(weatherData.get("WSD")))
                .build();
    }
}
