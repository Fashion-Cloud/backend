package com.techeer.fashioncloud.domain.post.exception;

import com.techeer.fashioncloud.global.error.exception.BusinessException;
import com.techeer.fashioncloud.global.error.ErrorCode;

public class BookNotFoundException extends BusinessException {
    public BookNotFoundException() {
        super(ErrorCode.LOOK_BOOK_NOT_FOUND);
    }
}