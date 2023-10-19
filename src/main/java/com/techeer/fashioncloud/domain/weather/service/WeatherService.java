package com.techeer.fashioncloud.domain.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.dto.external.WeatherApiRequest;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtFcst;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtNcst;
import com.techeer.fashioncloud.domain.weather.util.WeatherApiCaller;
import com.techeer.fashioncloud.domain.weather.util.WeatherApiParser;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final WeatherApiCaller weatherApiCaller;

    public WeatherInfoResponse getNowWeather(Integer nx, Integer ny) {
        try {
            UltraSrtNcstResponse ultraSrtNcstResponse = getUltraSrtNcst(new UltraSrtNcst(nx, ny));
            UltraSrtFcstResponse ultraSrtFcstResponse = getUltraSrtFcst(new UltraSrtFcst(nx, ny));

            return WeatherInfoResponse.getWeatherData(ultraSrtFcstResponse, ultraSrtNcstResponse);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.WEATHER_GET_FAILED);
        }
    }

    // 초단기예보 (하늘상태)
    private UltraSrtFcstResponse getUltraSrtFcst(UltraSrtFcst ultraSrtFcst) throws JsonProcessingException {

        String key = ultraSrtFcst.getKey();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            log.debug("cache hit: {}", key);
            String value = redisTemplate.opsForValue().get(key);
            return objectMapper.readValue(value, UltraSrtFcstResponse.class);
        }

        log.debug("cache miss: {}", key);
        WeatherApiRequest weatherApiReqest = WeatherApiRequest.builder()
                .path(UltraSrtFcst.REQ_URL)
                .queryParams(ultraSrtFcst.getReqQueryParams())
                .build();

        Mono<JsonNode> apiResponse = weatherApiCaller.get(weatherApiReqest);
        UltraSrtFcstResponse ultraSrtFcstResponse = apiResponse
                .map(WeatherApiParser::parse)
                .map(UltraSrtFcstResponse::of)
                .block();


        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(ultraSrtFcstResponse));
        return ultraSrtFcstResponse;
    }

    // 초단기실황예보 (하늘상태 외 날씨 데이터)

    private UltraSrtNcstResponse getUltraSrtNcst(UltraSrtNcst ultraSrtNcst) throws JsonProcessingException {

        String key = ultraSrtNcst.getKey();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            log.debug("cache hit: {}", key);

            String value = redisTemplate.opsForValue().get(key);
            return objectMapper.readValue(value, UltraSrtNcstResponse.class);
        }

        log.debug("cache miss: {}", key);
        WeatherApiRequest weatherApiRequest = WeatherApiRequest.builder()
                .path(UltraSrtNcst.REQ_URL)
                .queryParams(ultraSrtNcst.getReqQueryParams())
                .build();

        UltraSrtNcstResponse ultraSrtNcstResponse = weatherApiCaller.get(weatherApiRequest)
                .map(WeatherApiParser::parse)
                .map(UltraSrtNcstResponse::of)
                .block();

        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(ultraSrtNcstResponse));
        return ultraSrtNcstResponse;
    }
}

