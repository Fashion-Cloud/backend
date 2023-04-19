package com.techeer.fashioncloud.domain.post.exception;

import com.techeer.fashioncloud.global.exception.BusinessException;
import com.techeer.fashioncloud.global.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
