package com.techeer.fashioncloud.global.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtFcst;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtNcst;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WeatherCacheAspect {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Around("execution(* com.techeer.fashioncloud.domain.weather.service.WeatherService.getUltraSrtFcst(..)) && args(ultraSrtFcst)")
    public Object cacheUltraSrtFcst(ProceedingJoinPoint joinPoint, UltraSrtFcst ultraSrtFcst) throws Throwable {

        String key = "ultraSrtFcst:" + ultraSrtFcst.getNx() + "," + ultraSrtFcst.getNy() + ":" + ultraSrtFcst.getBaseTime();

        // cache hit
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String jsonString = redisTemplate.opsForValue().get(key);
            UltraSrtFcstResponse ultraSrtFcstResponse = objectMapper.readValue(jsonString, UltraSrtFcstResponse.class);

            log.debug("ultraSrtFcst cache hit! key: {}", key);
            return ResponseEntity.ok(ResultResponse.of(ResponseCode.WEATHER_GET_SUCCESS, ultraSrtFcstResponse));
        }

        // cache miss
        log.debug("ultraSrtFcst cache miss! key: {}", key);

        Object ultraSrtFcstResponse = joinPoint.proceed();
        String jsonString = objectMapper.writeValueAsString(ultraSrtFcstResponse);
        redisTemplate.opsForValue().set(key, jsonString);

        return ultraSrtFcstResponse;
    }

    @Around("execution(* com.techeer.fashioncloud.domain.weather.service.WeatherService.getUltraSrtNcst(..)) && args(ultraSrtNcst)")
    public Object cacheUltraSrtNcst(ProceedingJoinPoint joinPoint, UltraSrtNcst ultraSrtNcst) throws Throwable {

        String key = "ultraSrtNcst:" + ultraSrtNcst.getNx() + "," + ultraSrtNcst.getNy() + ":" + ultraSrtNcst.getBaseTime();

        // cache hit
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            String jsonString = redisTemplate.opsForValue().get(key);
            UltraSrtNcstResponse ultraSrtNcstResponse = objectMapper.readValue(jsonString, UltraSrtNcstResponse.class);

            log.debug("ultraSrtNcst cache hit! key: {}", key);
            return ResponseEntity.ok(ResultResponse.of(ResponseCode.WEATHER_GET_SUCCESS, ultraSrtNcstResponse));
        }

        // cache miss
        log.debug("ultraSrtNcst cache miss! key: {}", key);

        Object ultraSrtNcstResponse = joinPoint.proceed();
        String jsonString = objectMapper.writeValueAsString(ultraSrtNcstResponse);
        redisTemplate.opsForValue().set(key, jsonString);

        return ultraSrtNcstResponse;
    }
}