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
    LOOK_BOOK_POST_CREATE_SUCCESS(201, "룩북게시글 생성 성공"),

    // AUTH
    SIGNUP_SUCCESS(201, "회원가입 성공"),
    LOGIN_SUCCESS(201, "로그인 성공"),
    REISSUE_SUCCESS(201, "액세스 토큰 재발급 성공"),

    // USER
    USER_FOLLOW_SUCCESS(201, "유저 팔로우 성공"),
    USER_UNFOLLOW_SUCCESS(201, "유저 언팔로우 성공"),
    USER_FOLLOW_LIST_GET_SUCCESS(200, "유저 팔로우 리스트 조회 성공"),
    USER_INFO_GET_SUCCESS(200, "유저 정보 조회 성공"),

    ADDRESS_GET_SUCCESS(200, "주소 조회 성공"),

    IMAGE_UPLOAD_SUCCESS(201, "이미지 업로드 성공"),
    IMAGE_DELETE_SUCCESS(204, "이미지 삭제 성공"),
    WEATHER_GET_SUCCESS(200, "날씨 조회 성공");

    private final Integer status;
    private final String message;

}
