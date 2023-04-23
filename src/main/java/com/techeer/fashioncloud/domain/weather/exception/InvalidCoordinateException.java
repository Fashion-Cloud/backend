package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.exception.BusinessException;
import com.techeer.fashioncloud.global.exception.ErrorCode;

public class InvalidCoordinateException extends BusinessException {
    public InvalidCoordinateException() {
        super(ErrorCode.COORDINATE_INVALID);
    }
}
