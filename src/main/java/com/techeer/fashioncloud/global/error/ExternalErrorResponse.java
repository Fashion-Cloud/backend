package com.techeer.fashioncloud.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ExternalErrorResponse {
    private Integer status;
    private Integer kmaStatus; //기상청 서버 에러
    private String message;
    private LocalDateTime time;
}