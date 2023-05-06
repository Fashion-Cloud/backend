package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.error.exception.ExternalApiException;

public class KakaoApiException extends ExternalApiException{
    public KakaoApiException(Integer status, String message) {
            super(status, message);
    }
}
