package com.techeer.fashioncloud.domain.weather.dto;

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
    private Integer hourRainfall;
    private Integer humidity;
    private Integer rainfallType;
    private Double windSpeed;

    public WeatherInfoResponse getTotalWeather (
            UltraSrtFcstResponse ultraSrtFcstResponse,
            UltraSrtNcstResponse ultraSrtNcstResponse) {
        this.sky = ultraSrtFcstResponse.getSkyStatus();
        this.temperature = ultraSrtNcstResponse.getTemperature();
        this.hourRainfall = ultraSrtNcstResponse.getHourRainfall();
        this.humidity = ultraSrtNcstResponse.getHumidity();
        this.rainfallType = ultraSrtNcstResponse.getRainfallType();
        this.windSpeed = ultraSrtNcstResponse.getWindSpeed();

        return this;
    }
}
