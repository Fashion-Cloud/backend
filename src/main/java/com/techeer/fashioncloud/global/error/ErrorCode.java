package com.techeer.fashioncloud.global.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //도메인
    POST_NOT_FOUND(404, "POST NOT FOUND"),
    LOOK_BOOK_NOT_FOUND(404, "LOOK BOOK NOT FOUND"),
    LOOK_BOOK_POST_NOT_FOUND(404,"LOOK BOOK POST NOT FOUND"),

    //위치 관련
    LOCATION_INVALID(400, "LOCATION IS INVALID"),
    COORDINATE_INVALID(400, "COORDINATE IS INVALID"),

    // 외부 API
    API_PARSE_FAILED(500, "API PARSE FAILED"),
    API_BAD_REQUEST(400, "API_BAD_REQUEST"),
    API_SERVER_ERROR(500, "API_SERVER_ERROR");

    private Integer status;
    private String message;
}
