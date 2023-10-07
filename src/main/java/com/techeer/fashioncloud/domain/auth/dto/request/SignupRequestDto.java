package com.techeer.fashioncloud.domain.auth.dto.request;

import com.techeer.fashioncloud.domain.auth.enums.ROLE;
import com.techeer.fashioncloud.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "올바른 형식의 이메일을 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "이름을 입력하세요.")
    private String username;

    public User toEntity(String encodedPasword) {
        return User.builder()
                .email(email)
                .password(encodedPasword)
                .username(username)
                .role(ROLE.USER)
                .build();
    }
}