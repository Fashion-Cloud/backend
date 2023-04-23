package com.techeer.fashioncloud.global.error.exception;

import com.techeer.fashioncloud.global.error.ErrorCode;

public class ApiServerErrorException extends BusinessException {
    public ApiServerErrorException() {
        super(ErrorCode.API_SERVER_ERROR);
    }

}
