package com.techeer.fashioncloud.global.exception;

public class ApiParseException extends BusinessException {
    public ApiParseException() {
        super(ErrorCode.API_PARSE_FAILED);
    }

}
