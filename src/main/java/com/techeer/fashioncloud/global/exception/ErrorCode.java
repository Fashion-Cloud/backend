package com.techeer.fashioncloud.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //POST도메인
    POST_NOT_FOUND(404, "POST NOT FOUND"),

    //위치 관련
    LOCATION_INVALID(400, "LOCATION IS INVALID"),
    COORDINATE_INVALID(400, "COORDINATE IS INVALID");

    private Integer status;
    private String message;
}
