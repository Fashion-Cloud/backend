package com.techeer.fashioncloud.domain.User.exception;

public class UserIsDisabledException extends Exception {
    public UserIsDisabledException(String userIsDisabled) {
        super(userIsDisabled);
    }
}
