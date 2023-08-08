package com.techeer.fashioncloud.domain.User.dto.requestDto;

import lombok.Data;

@Data
public class AuthenticationDTO {

    private String email;

    private String password;

}
