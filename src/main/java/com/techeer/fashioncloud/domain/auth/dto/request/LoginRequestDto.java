package com.techeer.fashioncloud.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestDto {

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "올바른 형식의 이메일을 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
