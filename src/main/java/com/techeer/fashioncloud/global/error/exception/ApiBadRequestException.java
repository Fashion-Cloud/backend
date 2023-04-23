package com.techeer.fashioncloud.global.error.exception;

import com.techeer.fashioncloud.global.error.ErrorCode;

public class ApiBadRequestException extends BusinessException {
    public ApiBadRequestException() {
        super(ErrorCode.API_BAD_REQUEST);
    }
}
