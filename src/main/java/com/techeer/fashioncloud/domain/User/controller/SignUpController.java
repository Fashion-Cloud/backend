package com.techeer.fashioncloud.domain.User.controller;


import com.techeer.fashioncloud.domain.User.dto.requestDto.SignupDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.UserDTO;
import com.techeer.fashioncloud.domain.User.exception.UserAlreadyPresentException;
import com.techeer.fashioncloud.domain.User.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignUpController {




    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
        try {
            UserDTO createdUser = authService.createUser(signupDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserAlreadyPresentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
