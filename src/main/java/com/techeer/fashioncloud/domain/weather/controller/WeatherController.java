package com.techeer.fashioncloud.domain.weather.controller;

import com.techeer.fashioncloud.domain.Coordinate;
import com.techeer.fashioncloud.domain.Location;
import com.techeer.fashioncloud.domain.weather.constant.WeatherConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;


@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {

    //현재 위도와 경도를 가지고날씨를 받아오는 api
    // TODO : url에 현재 위도경도 정보 포함하면 사용자 위치 노출되므로 암호화하거나,, 다른 방법 찾기
    @GetMapping()
    public String getWeatherHere(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {

        //TODO: 위도 경도로 좌표값 구하기
        Coordinate coordinate = Location.getCoordinate(latitude, longitude);

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(WeatherConstant.BASE_URL+WeatherConstant.ULTRA_SRT_FCST);


        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        String buildUrl;
        String response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", "디코딩키")
                        .queryParam("numOfRows",19)
                        .queryParam("pageNo",1)
                        .queryParam("dataType","JSON")
                        .queryParam("base_date",20230322)
                        .queryParam("base_time",1730)
                        .queryParam("nx",55)
                        .queryParam("ny",123)
                .build()

                )
                .retrieve()
                .bodyToMono(String.class)
                .block();


        return response;

    }

    // 지정한 위도와 경도를 가지고 날씨를 받아오는 api

}
