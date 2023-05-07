package com.techeer.fashioncloud.global.error;

import com.techeer.fashioncloud.global.error.exception.BusinessException;
import com.techeer.fashioncloud.global.error.exception.ExternalApiException;
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

    // 내부 서버 에러
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

    // 외부 서버 에러
    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ExternalErrorResponse> handleException(ExternalApiException e) {
        Integer status = (HttpStatus.INTERNAL_SERVER_ERROR.value());
        Integer kmaStatus = e.getKmaStatus();
        String message = e.getMessage();
        log.error("Exception occurred - kmaStatus: {}, message: {}", kmaStatus, message);
        return new ResponseEntity<>((ExternalErrorResponse.builder()
                .status(status)
                .kmaStatus(kmaStatus)
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
