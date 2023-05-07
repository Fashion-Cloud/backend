package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.error.exception.ExternalApiException;

public class KakaoApiException extends ExternalApiException{
    public KakaoApiException(Integer status, String message) {
            super(500, status, message); //TODO: 임시로 500삽입해둠 이후 변경
    }
}
