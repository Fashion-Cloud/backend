package com.techeer.fashioncloud.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    POST_GET_SUCCESS(200, "게시글 조회 성공"),

    ADDRESS_GET_SUCCESS(200, "주소 조회 성공"),


    WEATHER_GET_SUCCESS(200, "날씨 조회 성공");

    private final Integer status;
    private final String message;

}
