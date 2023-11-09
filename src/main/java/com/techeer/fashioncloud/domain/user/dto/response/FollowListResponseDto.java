package com.techeer.fashioncloud.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowListResponseDto {
    private Integer followingCount;
    private Integer followerCount;
    private List<FollowUserInfoResponseDto> followingIdList;
    private List<FollowUserInfoResponseDto> followerIdList;
}
