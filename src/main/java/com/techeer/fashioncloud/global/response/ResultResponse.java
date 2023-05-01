package com.techeer.fashioncloud.global.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;

@Getter
public class ResultResponse<T> {
    private Integer code; //http status code
    private String message;
    private Object data;
    private LocalDateTime time;

    public ResultResponse(ResponseCode result, Object data) {
        this.code = result.getStatus();
        this.message = result.getMessage();
        this.data = data;
        this.time = LocalDateTime.now();
    }

    //응답할 data가 따로 없음
    public static ResultResponse of(ResponseCode result) {
        return new ResultResponse(result, Collections.EMPTY_LIST);
    }

    //응답할 데이터가 있음
    public static ResultResponse of(ResponseCode result, Object data) {
        return new ResultResponse(result, data);
    }
}
