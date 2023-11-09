package com.techeer.fashioncloud.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowUserInfoResponseDto {
    private Long userId;
    private String email;
    private String profileUrl;
    private String username;
}
