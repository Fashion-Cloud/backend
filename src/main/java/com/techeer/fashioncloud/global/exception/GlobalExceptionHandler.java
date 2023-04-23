package com.techeer.fashioncloud.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
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

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponse> handleException(ParseException e) {
        Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = "데이터 파싱 실패";
        log.error("Exception occurred - status: {}, message: {}", status, message);
        return new ResponseEntity<>((ErrorResponse.builder()
                .status(status)
                .message(message)
                .time(LocalDateTime.now())
                .build()), HttpStatus.valueOf(status));
    }
}
