package com.techeer.fashioncloud.domain.post.exception;

import com.techeer.fashioncloud.global.error.exception.BusinessException;
import com.techeer.fashioncloud.global.error.ErrorCode;

public class LookBookPostNotFoundException extends BusinessException {
    public LookBookPostNotFoundException() {
        super(ErrorCode.LOOK_BOOK_POST_NOT_FOUND);
    }
}