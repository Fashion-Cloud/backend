package com.techeer.fashioncloud.global.error.exception;

import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException {
    private Integer status;
    private String kmaStatus; //기상청 서버 응답
    private String message;

    public ExternalApiException(Integer status, String kmaStatus, String message) {
        super(message);
        this.status = status;
        this.kmaStatus = kmaStatus;
        this.message = message;
    }
}
