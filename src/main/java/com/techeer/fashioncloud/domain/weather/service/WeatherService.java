package com.techeer.fashioncloud.domain.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtFcst;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtNcst;
import com.techeer.fashioncloud.domain.weather.util.WeatherApiParser;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.global.config.WeatherConfig;
import com.techeer.fashioncloud.global.error.exception.ApiBadRequestException;
import com.techeer.fashioncloud.global.error.exception.ApiServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherConfig weatherConfig;
    private final RedisTemplate<String, WeatherInfoResponse> redisTemplate;

    public WeatherInfoResponse getNowWeather(Double latitude, Double longitude) {

        Coordinate coordinate = Location.getCoordinate(latitude, longitude);

        UltraSrtNcstResponse ultraSrtNcstResponse = getUltraSrtNcst(coordinate.getNx(), coordinate.getNy());
        UltraSrtFcstResponse ultraSrtFcstResponse = getUltraSrtFcst(coordinate.getNx(), coordinate.getNy());

        WeatherInfoResponse weatherInfo =  WeatherInfoResponse.getWeatherData(ultraSrtFcstResponse, ultraSrtNcstResponse);
        redisTemplate.opsForValue().set("weatherCache::"+coordinate.getNx()+","+coordinate.getNy(), weatherInfo);

        return weatherInfo;
    }

    // 초단기예보 (하늘상태)
    public UltraSrtFcstResponse getUltraSrtFcst(Integer nx, Integer ny) {

        UltraSrtFcst ultraSrtFcst = new UltraSrtFcst(nx, ny);
        Mono<JsonNode> responseMono = getResponseMono(UltraSrtFcst.REQ_URL, ultraSrtFcst.getReqQueryParams());

        return  responseMono
                .map(WeatherApiParser::parse)
                .map(UltraSrtFcstResponse::of)
                .block();
    }

    // 초단기실황예보 (하늘상태 외 날씨 데이터)
    public UltraSrtNcstResponse getUltraSrtNcst(Integer nx, Integer ny) {

        UltraSrtNcst ultraSrtNcst = new UltraSrtNcst(nx, ny);
        Mono<JsonNode> responseMono = getResponseMono(UltraSrtNcst.REQ_URL, ultraSrtNcst.getReqQueryParams());

        return  responseMono
                .map(WeatherApiParser::parse)
                .map(UltraSrtNcstResponse::of)
                .block();
    }

    public Mono<JsonNode> getResponseMono(String path, HashMap<String, Object> params) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(path);

        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        return webclient.get()
                .uri(uriBuilder -> {
                    uriBuilder.queryParam("serviceKey", weatherConfig.getDecodingKey());
                    uriBuilder.queryParam("dataType", "JSON"); //TODO: XML 이슈 해결
                    params.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })

                .exchangeToMono(response -> {

                    Integer httpStatusCode = response.statusCode().value();
                    HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode);

                    if (httpStatus.is2xxSuccessful()) {
                        return response.bodyToMono(JsonNode.class);
                    } else if (httpStatus.is4xxClientError()) {
                        log.error("Exception occurred - status: {}, message: {}", httpStatus, httpStatus.getReasonPhrase());
                        throw new ApiBadRequestException();
                    } else {
                        log.error("Exception occurred - status: {}, message: {}", httpStatus, httpStatus.getReasonPhrase());
                        throw new ApiServerErrorException();
                    }
                });
    }
}

