package com.techeer.fashioncloud.domain.user.dto.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private   String address;

    private   String email;

    private   String profileUrl;

    private   String username;

    private String introduction;

}



