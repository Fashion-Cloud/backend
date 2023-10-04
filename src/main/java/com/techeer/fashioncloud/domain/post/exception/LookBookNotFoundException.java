package com.techeer.fashioncloud.domain.post.exception;

import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;

public class LookBookNotFoundException extends BusinessException {
    public LookBookNotFoundException() {
        super(ErrorCode.LOOK_BOOK_NOT_FOUND);
    }
}