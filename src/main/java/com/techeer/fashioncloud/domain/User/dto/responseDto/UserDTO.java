package com.techeer.fashioncloud.domain.User.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String profileUrl;

    private String address;

    private String nickname;

    private String email;

}
