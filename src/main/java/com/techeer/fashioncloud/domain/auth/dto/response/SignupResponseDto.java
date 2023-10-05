package com.techeer.fashioncloud.domain.auth.dto.response;

import com.techeer.fashioncloud.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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