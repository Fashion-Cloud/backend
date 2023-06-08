package com.techeer.fashioncloud.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    //POST
    POST_GET_SUCCESS(200, "게시글 조회 성공"),
    POST_CREATE_SUCCESS(201, "게시글 생성 성공"),
    POST_DELETE_SUCCESS(204, "게시글 삭제 성공"),
    POST_UPDATE_SUCCESS(200, "게시글 업데이트 성공"),

    //LOOK_BOOK
    LOOK_BOOK_GET_SUCCESS(200, "룩북 조회 성공"),
    LOOK_BOOK_CREATE_SUCCESS(201, "룩북 생성 성공"),

    //LOOK_BOOK_POST
    LOOK_BOOK_POST_GET_SUCCESS(200, "룩북게시글 조회 성공"),
    LOOK_BOOK_POST_CREATE_SUCCESS(201, "룩북게시글 생성 성공"),


    ADDRESS_GET_SUCCESS(200, "주소 조회 성공"),

    IMAGE_UPLOAD_SUCCESS(201, "이미지 업로드 성공"),
    IMAGE_DELETE_SUCCESS(204,"이미지 삭제 성공"),
    WEATHER_GET_SUCCESS(200, "날씨 조회 성공");

    private final Integer status;
    private final String message;

}
