package com.techeer.fashioncloud.domain.User.dto.requestDto;

import lombok.Data;

@Data
public class SignupDTO {

    private String profileUrl;

    private String address;

    private String nickname;

    private String email;

    private String password;

}
