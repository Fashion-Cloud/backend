package com.techeer.fashioncloud.global.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime time;
}



