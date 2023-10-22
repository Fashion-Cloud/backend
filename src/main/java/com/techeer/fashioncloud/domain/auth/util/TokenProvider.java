package com.techeer.fashioncloud.domain.auth.util;

import com.techeer.fashioncloud.domain.auth.service.CustomUserDetailService;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    public static final String TOKEN_PREFIX = "Bearer ";


    @Value("${jwt.access-token-expire-minute}")
    private Integer accessTokenExpireMinute;

    @Value("${jwt.refresh-token-expire-minute}")
    private Integer refreshTokenExpireMinute;


    @Value("${jwt.access-secret}")
    private String accessSecret;

    @Value("${jwt.refresh-secret}")
    private String refreshSecret;

    private Key accessKey;
    private Key refreshKey;

    private JwtParser accessTokenParser;
    private JwtParser refreshTokenParser;

    private final CustomUserDetailService customUserDetailsService;

    @PostConstruct
    public void init() {
        byte[] accessKeyBytes = Base64.getDecoder().decode(accessSecret);
        byte[] refreshKeyBytes = Base64.getDecoder().decode(refreshSecret);
        this.accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        this.refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
        this.accessTokenParser = Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build();
        this.refreshTokenParser = Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build();
    }

    public String createAccessToken(Authentication authentication, String authorities) {
        long now = System.currentTimeMillis();
        Date nowDate = new Date();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // subject: 사용자 식별자 정보 담음
                .claim(AUTHORITIES_KEY, authorities) // 페이로드에 인증 정보 저장
                .setIssuedAt(nowDate)
                .setExpiration(new Date(now + accessTokenExpireMinute * 60 * 1000))
                .signWith(accessKey, SignatureAlgorithm.HS512)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Authentication authentication, String authorities) {
        long now = System.currentTimeMillis();
        Date nowDate = new Date();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 식별자
                .claim(AUTHORITIES_KEY, authorities) // 페이로드에 인증 정보 저장
                .setIssuedAt(nowDate)
                .setExpiration(new Date(now + refreshTokenExpireMinute * 60 * 1000))
                .signWith(refreshKey, SignatureAlgorithm.HS512)
                .compact();

        return refreshToken;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseAccessClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new BusinessException(ErrorCode.JWT_AUTHORITY_NOT_FOUND);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), authorities);
    }

    public boolean validateAccessToken(String accessToken) {
        accessTokenParser.parseClaimsJws(accessToken);
        return true;
    }

    public boolean validateRefreshToken(String refreshToken) {
        accessTokenParser.parseClaimsJws(refreshToken);
        return true;
    }

    private Claims parseAccessClaims(String accessToken) {
        return refreshTokenParser
                .parseClaimsJws(accessToken)
                .getBody();
    }

    private Claims parseRefreshClaims(String refreshToken) {

        return refreshTokenParser
                .parseClaimsJws(refreshToken)
                .getBody();
    }

    private String extractJwtToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(TOKEN_PREFIX.length()); // "Bearer " 이후 jwt토큰
        } else {
            return null;
        }
    }
}
