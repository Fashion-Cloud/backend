package com.techeer.fashioncloud.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
        Integer status = e.getErrorCode().getStatus();
        String message = e.getMessage();
        log.error("Exception occurred - status: {}, message: {}", status, message);
        return new ResponseEntity<>((ErrorResponse.builder()
                .status(status)
                .message(message)
                .time(LocalDateTime.now())
                .build()), HttpStatus.valueOf(status));
    }
}
