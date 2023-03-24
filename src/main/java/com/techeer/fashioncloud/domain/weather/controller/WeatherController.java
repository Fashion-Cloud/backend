package com.techeer.fashioncloud.domain.weather.controller;

import com.techeer.fashioncloud.domain.Coordinate;
import com.techeer.fashioncloud.domain.Location;
import com.techeer.fashioncloud.domain.weather.dto.WeatherResponse;
import com.techeer.fashioncloud.domain.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
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

    //현재 위도와 경도를 가지고 날씨를 받아오는 api
    //TODO : url에 현재 위도경도 정보 포함하면 사용자 위치 노출되므로 암호화하거나,, 다른 방법 찾기
    @GetMapping()
    public ResponseEntity<WeatherResponse> getWeatherHere(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) throws ParseException, org.json.simple.parser.ParseException {

        //TODO: 위도 경도로 좌표값 구하기
        Coordinate coordinate = Location.getCoordinate(latitude, longitude);

        WeatherResponse weather = weatherService.getNowWeather(coordinate.getNx(), coordinate.getNy());
        return ResponseEntity.ok(weather);

    }

    // 지정한 위도와 경도를 가지고 날씨를 받아오는 api

}
