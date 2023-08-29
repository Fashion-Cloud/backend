package com.techeer.fashioncloud.domain.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.dto.external.WeatherApiRequest;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtFcst;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtNcst;
import com.techeer.fashioncloud.domain.weather.util.WeatherApiCaller;
import com.techeer.fashioncloud.domain.weather.util.WeatherApiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final RedisTemplate<String, WeatherInfoResponse> redisTemplate;
    private final WeatherApiCaller weatherApiCaller;

    @Cacheable(value = "weather", key = "#nx + ',' + #ny")
    public WeatherInfoResponse getNowWeather(Integer nx, Integer ny) {

        UltraSrtNcstResponse ultraSrtNcstResponse = getUltraSrtNcst(nx, ny);
        UltraSrtFcstResponse ultraSrtFcstResponse = getUltraSrtFcst(nx, ny);

        WeatherInfoResponse weatherInfo =  WeatherInfoResponse.getWeatherData(ultraSrtFcstResponse, ultraSrtNcstResponse);
//        redisTemplate.opsForValue().set("weatherCache::"+nx+","+ny, weatherInfo);

        return weatherInfo;
    }

    // 초단기예보 (하늘상태)
    public UltraSrtFcstResponse getUltraSrtFcst(Integer nx, Integer ny) {

        UltraSrtFcst ultraSrtFcst = new UltraSrtFcst(nx, ny);
        WeatherApiRequest weatherApiReqest = WeatherApiRequest.builder()
                .path(UltraSrtFcst.REQ_URL)
                .queryParams(ultraSrtFcst.getReqQueryParams())
                .build();

        Mono<JsonNode> apiResponse = weatherApiCaller.get(weatherApiReqest);
        return  apiResponse
                .map(WeatherApiParser::parse)
                .map(UltraSrtFcstResponse::of)
                .block();
    }

    // 초단기실황예보 (하늘상태 외 날씨 데이터)
    public UltraSrtNcstResponse getUltraSrtNcst(Integer nx, Integer ny) {

        UltraSrtNcst ultraSrtNcst = new UltraSrtNcst(nx, ny);

        WeatherApiRequest weatherApiRequest = WeatherApiRequest.builder()
                .path(UltraSrtNcst.REQ_URL)
                .queryParams(ultraSrtNcst.getReqQueryParams())
                .build();

        return  weatherApiCaller.get(weatherApiRequest)
                .map(WeatherApiParser::parse)
                .map(UltraSrtNcstResponse::of)
                .block();
    }
}