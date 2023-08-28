package com.techeer.fashioncloud.domain.weather.controller;

import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.service.WeatherService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping()
    public ResponseEntity<ResultResponse> getWeatherHere(
            @RequestParam Double latitude,
            @RequestParam Double longitude
        ) {

        WeatherInfoResponse weather = weatherService.getNowWeather(latitude, longitude);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.WEATHER_GET_SUCCESS, weather));
    }
}