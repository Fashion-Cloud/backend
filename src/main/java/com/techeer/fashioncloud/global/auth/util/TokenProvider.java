package com.techeer.fashioncloud.global.auth.util;

import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class TokenProvider {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
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
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseAccessClaims(String accessToken) {

        return accessTokenParser
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