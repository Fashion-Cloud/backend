package com.techeer.fashioncloud.global.error;

import com.techeer.fashioncloud.global.error.exception.BusinessException;
import com.techeer.fashioncloud.global.error.exception.ExternalApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
        log.error(String.valueOf(e.getClass()));
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
//        Integer kmaStatus = e.getKmaStatus();
        String message = e.getMessage();
//        log.error("Exception occurred - kmaStatus: {}, message: {}", kmaStatus, message);
        log.error(String.valueOf(e.getClass()));
        return new ResponseEntity<>((ExternalErrorResponse.builder()
                .status(status)
//                .kmaStatus(kmaStatus) //기상청 응답코드
                .message(message)
                .time(LocalDateTime.now())
                .build()), HttpStatus.valueOf(status));
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponse> handleException(ParseException e) {
        Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = "exception occurred while parsing json data";
        log.error("Exception occurred - status: {}, message: {}", status, message);
        return new ResponseEntity<>((ErrorResponse.builder()
                .status(status)
                .message(message)
                .time(LocalDateTime.now())
                .build()), HttpStatus.valueOf(status));
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleException(WebClientResponseException e) {
        Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = "openAPI 호출 에러";
        log.error("WebClientResponseException occurred (WebClient) - status: {}, message: {}", status, message);
        return new ResponseEntity<>((ErrorResponse.builder()
                .status(status)
                .message(message)
                .time(LocalDateTime.now())
                .build()), HttpStatus.valueOf(status));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
        Integer status;
        String message;

        if (e instanceof ExpiredJwtException) {
            status = HttpStatus.UNAUTHORIZED.value();
            message = "JWT 토큰 만료";
            log.error("ExpiredJwtException occurred - status: {}, message: {}", status, message);
        } else if (e instanceof UnsupportedJwtException) {
            status = HttpStatus.BAD_REQUEST.value();
            message = "지원하지 않는 JWT 토큰";
            log.error("UnsupportedJwtException occurred - status: {}, message: {}", status, message);
        } else if (e instanceof MalformedJwtException) {
            status = HttpStatus.BAD_REQUEST.value();
            message = "잘못된 형식의 JWT 토큰";
            log.error("MalformedJwtException occurred - status: {}, message: {}", status, message);
        } else if (e instanceof SignatureException) {
            status = HttpStatus.UNAUTHORIZED.value();
            message = "JWT 서명 오류";
            log.error("SignatureException occurred - status: {}, message: {}", status, message);
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "JWT 토큰 에러";
            log.error("JwtException occurred (JWT) - status: {}, message: {}", status, message);
        }

        return new ResponseEntity<>(ErrorResponse.builder()
                .status(status)
                .message(message)
                .time(LocalDateTime.now())
                .build(), HttpStatus.valueOf(status));
    }


}