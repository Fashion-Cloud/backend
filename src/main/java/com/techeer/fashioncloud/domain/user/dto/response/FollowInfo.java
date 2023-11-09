package com.techeer.fashioncloud.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowInfo {
    private Integer followerCount;
    private Integer followingCount;
    private Boolean isFollowing;
}
