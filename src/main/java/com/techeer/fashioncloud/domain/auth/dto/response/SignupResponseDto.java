package com.techeer.fashioncloud.domain.auth.dto.response;

import com.techeer.fashioncloud.domain.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignupResponseDto {
    private String email;
    private String username;

    public static SignupResponseDto of(User user) {

        return SignupResponseDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}