package com.techeer.fashioncloud.domain.auth.service;

import com.techeer.fashioncloud.domain.auth.dto.Token;
import com.techeer.fashioncloud.domain.auth.dto.request.LoginRequestDto;
import com.techeer.fashioncloud.domain.auth.util.TokenProvider;
import com.techeer.fashioncloud.domain.auth.dto.request.SignupRequestDto;
import com.techeer.fashioncloud.domain.auth.dto.response.SignupResponseDto;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupReqDto) {

        if(userRepository.existsByEmail(signupReqDto.getEmail())) {
            throw new BusinessException(ErrorCode.SIGNUP_EMAIL_DUPLICATED);
        }

        String encodedPassword = passwordEncoder.encode(signupReqDto.getPassword());
        User user = signupReqDto.toEntity(encodedPassword);

        return SignupResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public Token login(LoginRequestDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = tokenProvider.createAccessToken(authentication, authorities);
        String refreshToken = tokenProvider.createRefreshToken(authentication, authorities);

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
