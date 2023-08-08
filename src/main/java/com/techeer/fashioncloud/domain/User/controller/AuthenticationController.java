package com.techeer.fashioncloud.domain.User.controller;


import com.techeer.fashioncloud.domain.User.dto.requestDto.AuthenticationDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.AuthenticationResponseDTO;
import com.techeer.fashioncloud.domain.User.exception.UserIsDisabledException;
import com.techeer.fashioncloud.domain.User.exception.WrongCredintialsException;
import com.techeer.fashioncloud.domain.User.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws IOException {
        try {
            AuthenticationResponseDTO authenticationResponseDTO = authenticationService.createJWTToken(authenticationDTO);
            return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
        } catch (WrongCredintialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserIsDisabledException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
