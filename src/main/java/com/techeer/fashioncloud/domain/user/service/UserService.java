package com.techeer.fashioncloud.domain.user.service;

import com.techeer.fashioncloud.domain.user.dto.response.FollowInfo;
import com.techeer.fashioncloud.domain.user.dto.response.FollowListResponseDto;
import com.techeer.fashioncloud.domain.user.dto.response.FollowUserInfoResponseDto;
import com.techeer.fashioncloud.domain.user.dto.response.UserInfoResponse;
import com.techeer.fashioncloud.domain.user.entity.Follow;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.FollowRepository;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(User fromUser, Long userId) {

        User toUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (checkSelfFollow(fromUser, toUser)) {
            throw new BusinessException(ErrorCode.USER_SELF_FOLLOW);
        }
        if (checkFollowed(fromUser, toUser)) {
            throw new BusinessException(ErrorCode.USER_ALREADY_FOLLOWED);
        }

        Follow follow = Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(User fromUser, Long userId) {

        User toUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (checkSelfFollow(fromUser, toUser)) {
            throw new BusinessException(ErrorCode.USER_SELF_UNFOLLOW);
        }
        if (!checkFollowed(fromUser, toUser)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOLLOWED);
        }

        followRepository.deleteByFromUserIdAndToUserId(fromUser.getId(), toUser.getId());
    }

    @Transactional(readOnly = true)
    public FollowListResponseDto getFollowList(User loginUser) {

        List<FollowUserInfoResponseDto> followingIdList = followRepository.findFollowingsByUserId(loginUser.getId());
        List<FollowUserInfoResponseDto> followerIdList = followRepository.findFollowersByUserId(loginUser.getId());

        return FollowListResponseDto.builder()
                .followingCount(followingIdList.size())
                .followerCount(followerIdList.size())
                .followingIdList(followingIdList)
                .followerIdList(followerIdList)
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId, User loginUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .image(user.getProfileUrl())
                .address(user.getAddress())
                .followInfo(getFollowInfo(user, loginUser))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private boolean checkFollowed(User fromUser, User toUser) {
        return followRepository.countByFromUserIdAndToUserId(fromUser.getId(), toUser.getId()) > 0;
    }

    private boolean checkSelfFollow(User fromUser, User toUser) {
        return Objects.equals(fromUser.getId(), toUser.getId());
    }

    private FollowInfo getFollowInfo(User user, User loginUser) {
        return FollowInfo.builder()
                .followerCount(followRepository.countByToUser(user))
                .followingCount(followRepository.countByFromUser(user))
                .isFollowing(checkFollowed(loginUser, user))
                .build();
    }
}