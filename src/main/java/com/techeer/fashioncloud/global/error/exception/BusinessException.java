package com.techeer.fashioncloud.global.error.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    HttpStatus errorCode;
    public BusinessException(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}