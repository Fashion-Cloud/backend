package com.techeer.fashioncloud.domain.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import com.techeer.fashioncloud.domain.weather.position.LocationConverter;
import com.techeer.fashioncloud.domain.weather.service.WeatherService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "날씨 반환", description ="위경도를 이용해 날씨를 반환한다.")
    public ResponseEntity<ResultResponse> getWeatherHere(
            @Parameter(name="latitude") @RequestParam Double latitude,
            @Parameter(name="longitude") @RequestParam Double longitude
        ) throws JsonProcessingException {

        Coordinate coord = LocationConverter.toCoord(latitude, longitude);
        WeatherInfoResponse weather = weatherService.getNowWeather(coord.getNx(), coord.getNy());
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.WEATHER_GET_SUCCESS, weather));
    }
}