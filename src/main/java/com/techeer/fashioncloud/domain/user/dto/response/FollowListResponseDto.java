package com.techeer.fashioncloud.domain.user.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FollowListResponseDto {
    private Integer followingCount;
    private Integer followerCount;
    private List<FollowInfoResponseDto> followingIdList;
    private List<FollowInfoResponseDto> followerIdList;
}
