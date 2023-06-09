package com.techeer.fashioncloud.domain.weather.exception;

import com.techeer.fashioncloud.global.error.exception.BusinessException;
import com.techeer.fashioncloud.global.error.ErrorCode;

public class InvalidLocationException extends BusinessException {
    public InvalidLocationException() {
        super(ErrorCode.LOCATION_INVALID);
    }
}
