package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.error.exception.ExternalApiException;

// 기상청 서버 비정상 응답시 발생
public class WeatherApiException extends ExternalApiException {

    public WeatherApiException(String code, String message) {
        super(code, message);
    }
}