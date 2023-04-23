package com.techeer.fashioncloud.global.error.exception;

import com.techeer.fashioncloud.global.error.ErrorCode;

public class ApiParseException extends BusinessException {
    public ApiParseException() {
        super(ErrorCode.API_PARSE_FAILED);
    }

}
