package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;

public class InvalidRainCodeException extends BusinessException {
    public InvalidRainCodeException() {
        super(ErrorCode.RAINCODE_INVALID);
    }
}