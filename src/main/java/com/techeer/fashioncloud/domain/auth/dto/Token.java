package com.techeer.fashioncloud.domain.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class Token {
    private String accessToken;
    private String refreshToken;
}
