package com.techeer.fashioncloud.domain.user.service;

import com.techeer.fashioncloud.domain.user.entity.Follow;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.FollowRepository;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(UserDetails LoginUser, Long userId) {

        User fromUser = userRepository.findByEmail(LoginUser.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
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
    public void unfollow(UserDetails LoginUser, Long userId) {

        User fromUser = userRepository.findByEmail(LoginUser.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
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

    private boolean checkFollowed(User fromUser, User toUser) {
        return followRepository.countByFromUserIdAndToUserId(fromUser.getId(), toUser.getId()) > 0;
    }

    private boolean checkSelfFollow(User fromUser, User toUser) {
        return Objects.equals(fromUser.getId(), toUser.getId());
    }
}
