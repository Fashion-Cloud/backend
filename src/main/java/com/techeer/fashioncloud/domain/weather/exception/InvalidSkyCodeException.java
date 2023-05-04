package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;

public class InvalidSkyCodeException extends BusinessException {
    public InvalidSkyCodeException() {
        super(ErrorCode.SKYCODE_INVALID);
    }
}
