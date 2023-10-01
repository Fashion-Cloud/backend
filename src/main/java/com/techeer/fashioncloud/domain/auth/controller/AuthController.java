package com.techeer.fashioncloud.domain.auth.controller;

import com.techeer.fashioncloud.domain.auth.dto.request.LoginRequestDto;
import com.techeer.fashioncloud.domain.auth.dto.request.SignupRequestDto;
import com.techeer.fashioncloud.domain.auth.dto.response.SignupResponseDto;
import com.techeer.fashioncloud.domain.auth.dto.response.Token;
import com.techeer.fashioncloud.domain.auth.service.AuthService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "auth", description = "인증 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입한다.")
    public ResponseEntity<ResultResponse> signup(
            @Valid
            @RequestBody SignupRequestDto signupReqDto
    ) {
        SignupResponseDto signupResDto = authService.signup(signupReqDto);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.SIGNUP_SUCCESS, signupResDto));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 성공시 jwt 토큰을 반환한다.")
    public ResponseEntity<ResultResponse> login(
            @Valid
            @RequestBody LoginRequestDto dto
    ) {
        Token token = authService.login(dto);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOGIN_SUCCESS, token));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급", description = "refreshToken으로 accessToken을 재발급받는다.")
    public ResponseEntity<ResultResponse> login(
            @Valid
            @RequestBody Token token
    ) {
        Token newToken = authService.reissue(token);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.REISSUE_SUCCESS, newToken));
    }
}