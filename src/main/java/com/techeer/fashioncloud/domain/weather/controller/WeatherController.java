package com.techeer.fashioncloud.domain.weather.controller;

import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.domain.weather.service.WeatherService;
import com.techeer.fashioncloud.global.dto.LocationDto;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {
    private final WeatherService weatherService;

    //현재 위도와 경도를 가지고 날씨를 받아오는 api
    //TODO : url에 현재 위도경도 정보 포함하면 사용자 위치 노출되므로 암호화하거나,, 다른 방법 찾기



    @GetMapping()
    public ResponseEntity<ResultResponse> getWeatherHere(
            @RequestBody LocationDto location
            ) throws ParseException, org.json.simple.parser.ParseException {

        WeatherInfoResponse weather = weatherService.getNowWeather(Location.getCoordinate(location.getLatitude(), location.getLongitude()));
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.WEATHER_GET_SUCCESS, weather));
    }
}
