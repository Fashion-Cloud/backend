package com.techeer.fashioncloud.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalApiException extends RuntimeException {
    private String status;
    private String message;
}
