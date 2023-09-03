package com.techeer.fashioncloud.domain.User.service;

import com.techeer.fashioncloud.domain.User.dto.requestDto.SignupDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.UserDTO;
import com.techeer.fashioncloud.domain.User.exception.UserAlreadyPresentException;
import com.techeer.fashioncloud.domain.User.model.ERole;

public interface AuthService {
    UserDTO createUser(SignupDTO signUpDto) throws UserAlreadyPresentException;
}
