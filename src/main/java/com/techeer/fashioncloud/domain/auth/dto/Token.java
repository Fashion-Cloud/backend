package com.techeer.fashioncloud.domain.auth.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PRIVATE)
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class Token {
    private String accessToken;
    private String refreshToken;
}
