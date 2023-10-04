package com.techeer.fashioncloud.domain.auth.service;

import com.techeer.fashioncloud.domain.auth.dto.request.LoginRequestDto;
import com.techeer.fashioncloud.domain.auth.dto.request.SignupRequestDto;
import com.techeer.fashioncloud.domain.auth.dto.response.SignupResponseDto;
import com.techeer.fashioncloud.domain.auth.dto.response.Token;
import com.techeer.fashioncloud.domain.auth.util.TokenProvider;
import com.techeer.fashioncloud.domain.post.service.S3Service;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, String> redisTemplate;
    private final S3Service s3Service;
    private final String refreshTokenKeyPrefix = "refreshToken:";

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupReqDto) {

        if (userRepository.existsByEmail(signupReqDto.getEmail())) {
            throw new BusinessException(ErrorCode.SIGNUP_EMAIL_DUPLICATED);
        }

        String encodedPassword = passwordEncoder.encode(signupReqDto.getPassword());
        //에러 처리?d
        String profileImageUrl = s3Service.uploadImage(signupReqDto.getProfileImage());

        User user = signupReqDto.toEntity(encodedPassword, profileImageUrl);

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

        redisTemplate.opsForValue().set(refreshTokenKeyPrefix + authentication.getName(), refreshToken);

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public Token reissue(Token token) {

        // refreshToken 검증
        if (!tokenProvider.validateToken(token.getRefreshToken())) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        Authentication authentication = tokenProvider.getAuthentication(token.getAccessToken());

        // accessToken의 사용자 이메일 정보로 refreshToken 조회
        String existRefreshToken = redisTemplate.opsForValue().get(refreshTokenKeyPrefix + authentication.getName()
        );

        if (!Objects.equals(existRefreshToken, token.getRefreshToken())) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_INVALID);
        }

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String newAccessToken = tokenProvider.createAccessToken(authentication, authorities);
        String newRefreshToken = tokenProvider.createRefreshToken(authentication, authorities);

        Token newToken = Token.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

        // 기존 refreshToken 업데이트
        // TODO: 트랜잭션 고려
        redisTemplate.opsForValue().set(refreshTokenKeyPrefix + authentication.getName(), newRefreshToken);

        return Token.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
