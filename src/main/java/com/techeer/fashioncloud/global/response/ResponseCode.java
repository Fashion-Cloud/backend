package com.techeer.fashioncloud.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    POST_GET_SUCCESS(200, "게시글 조회 성공");

    private final Integer status;
    private final String message;

}
