package com.techeer.fashioncloud.domain.weather.dto;

import com.techeer.fashioncloud.domain.weather.util.WindChillCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class WeatherInfoResponse {
    private Integer sky;
    private Double temperature;
    private Double hourRainfall;
    private Integer humidity;
    private Integer rainfallType;
    private Double windSpeed;
    private Double windChill;

    public static WeatherInfoResponse getWeatherData (
            UltraSrtFcstResponse ultraSrtFcstResponse,
            UltraSrtNcstResponse ultraSrtNcstResponse)
    {
        return WeatherInfoResponse.builder()
                .sky(ultraSrtFcstResponse.getSkyStatus())
                .temperature(ultraSrtNcstResponse.getTemperature())
                .hourRainfall(ultraSrtNcstResponse.getHourRainfall())
                .humidity(ultraSrtNcstResponse.getHumidity())
                .rainfallType(ultraSrtNcstResponse.getRainfallType())
                .windSpeed(ultraSrtNcstResponse.getWindSpeed())
                .windChill(WindChillCalculator.getWindChill(
                        ultraSrtNcstResponse.getTemperature(),
                        ultraSrtNcstResponse.getWindSpeed()))
                .build();
    }
}
