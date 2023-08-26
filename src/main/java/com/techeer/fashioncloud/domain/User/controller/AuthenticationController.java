package com.techeer.fashioncloud.domain.User.controller;

import com.techeer.fashioncloud.domain.User.dto.requestDto.AuthenticationDTO;
import com.techeer.fashioncloud.domain.User.dto.requestDto.SignupDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.AuthenticationResponseDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.UserDTO;
import com.techeer.fashioncloud.domain.User.exception.UserAlreadyPresentException;
import com.techeer.fashioncloud.domain.User.exception.UserIsDisabledException;
import com.techeer.fashioncloud.domain.User.exception.WrongCredintialsException;
import com.techeer.fashioncloud.domain.User.service.AuthService;
import com.techeer.fashioncloud.domain.User.service.AuthenticationService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws IOException {
        try {
            AuthenticationResponseDTO authenticationResponseDTO = authenticationService.createJWTToken(authenticationDTO);
            return ResponseEntity.ok(ResultResponse.of(ResponseCode.USER_CREATE_SUCCESS, authenticationResponseDTO));

        } catch (WrongCredintialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserIsDisabledException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
        try {

            UserDTO createdUser = authService.createUser(signupDTO);
            return ResponseEntity.ok(ResultResponse.of(ResponseCode.USER_LOGIN_SUCCESS, createdUser));
        } catch (UserAlreadyPresentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
