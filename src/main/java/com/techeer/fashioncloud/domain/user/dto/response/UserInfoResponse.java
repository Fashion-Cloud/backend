package com.techeer.fashioncloud.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String email;
    private String username;
    private String image;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
