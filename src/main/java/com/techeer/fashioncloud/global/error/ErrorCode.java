package com.techeer.fashioncloud.global.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //POST도메인
    POST_NOT_FOUND(404, "POST_NOT_FOUND"),

    //위치 관련
    LOCATION_INVALID(400, "INVALID_LOCATION"),
    COORDINATE_INVALID(400, "INVALID_COORDINATE"),

    //날씨 관련
    SKYCODE_INVALID(400, "INVALID_SKYCODE"),
    RAINCODE_INVALID(400, "INVALID_RAINCODE"),


    // 외부 API
    API_PARSE_FAILED(500, "API PARSE FAILED"),
    API_BAD_REQUEST(400, "API_BAD_REQUEST"),
    API_SERVER_ERROR(500, "API_SERVER_ERROR");

    private Integer status;
    private String message;
}
