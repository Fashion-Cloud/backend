package com.techeer.fashioncloud.global.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //POST도메인
    POST_NOT_FOUND(404, "POST NOT FOUND");

    private Integer status;
    private String message;
}
