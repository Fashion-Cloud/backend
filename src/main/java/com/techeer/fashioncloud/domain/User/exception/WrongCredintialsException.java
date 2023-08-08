package com.techeer.fashioncloud.domain.User.exception;

public class WrongCredintialsException extends Exception {
    public WrongCredintialsException(String usernameOrPasswordIncorrect) {
        super(usernameOrPasswordIncorrect);
    }
}
