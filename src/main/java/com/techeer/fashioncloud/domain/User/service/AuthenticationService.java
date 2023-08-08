package com.techeer.fashioncloud.domain.User.service;

import com.techeer.fashioncloud.domain.User.dto.requestDto.AuthenticationDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.AuthenticationResponseDTO;
import com.techeer.fashioncloud.domain.User.exception.UserIsDisabledException;
import com.techeer.fashioncloud.domain.User.exception.WrongCredintialsException;

public interface AuthenticationService {
    public AuthenticationResponseDTO createJWTToken(AuthenticationDTO authenticationDTO) throws WrongCredintialsException, UserIsDisabledException;
}
