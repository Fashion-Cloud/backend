package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.error.exception.BusinessException;
import com.techeer.fashioncloud.global.error.ErrorCode;

public class InvalidCoordinateException extends BusinessException {
    public InvalidCoordinateException() {
        super(ErrorCode.COORDINATE_INVALID);
    }
}
