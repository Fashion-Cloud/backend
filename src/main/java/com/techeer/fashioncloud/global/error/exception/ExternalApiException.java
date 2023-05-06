package com.techeer.fashioncloud.global.error.exception;

import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException {
    private Integer status;
    private String message;

    public ExternalApiException(Integer status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
