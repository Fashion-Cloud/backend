package com.techeer.fashioncloud.domain.User.service;

import com.techeer.fashioncloud.domain.user.dto.response.FollowInfoResponseDto;
import com.techeer.fashioncloud.domain.user.dto.response.FollowListResponseDto;
import com.techeer.fashioncloud.domain.user.dto.response.UserInfoResponse;
import com.techeer.fashioncloud.domain.user.entity.Follow;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.FollowRepository;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import com.techeer.fashioncloud.domain.user.service.UserService;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFollow_Successful() {
        // given
        User fromUser = User.builder()
                .id(1L)
                .build();
        Long userId = 1L;
        User toUser = User.builder()
                .id(2L)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(toUser));

        // when
        userService.follow(fromUser, userId);

        // then
        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    public void testFollow_SelfFollow() {
        // given
        User fromUser = User.builder()
                .id(1L)
                .build();
        Long userId = 1L;
        User toUser = User.builder()
                .id(1L)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(toUser));

        // when, then
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.follow(fromUser, userId));
        assertEquals(ErrorCode.USER_SELF_FOLLOW, exception.getErrorCode());
        verify(followRepository, never()).save(any(Follow.class));
    }

    @Test
    public void testUnfollow_Successful() {
        // given
        User fromUser = User.builder()
                .id(1L)
                .build();
        Long userId = 2L;
        User toUser = User.builder()
                .id(userId)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(toUser));
        when(followRepository.countByFromUserIdAndToUserId(fromUser.getId(), toUser.getId())).thenReturn(1);

        // when
        userService.unfollow(fromUser, userId);

        // then
        verify(followRepository, times(1)).deleteByFromUserIdAndToUserId(fromUser.getId(), toUser.getId());
    }

    @Test
    public void testUnfollow_NotFollowed() {
        // given
        User fromUser = User.builder()
                .id(1L)
                .build();
        Long userId = 2L;
        User toUser = User.builder()
                .id(userId)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(toUser));
        when(followRepository.countByFromUserIdAndToUserId(fromUser.getId(), toUser.getId())).thenReturn(0);

        // when, then
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.unfollow(fromUser, userId));
        assertEquals(ErrorCode.USER_NOT_FOLLOWED, exception.getErrorCode());
        verify(followRepository, never()).deleteByFromUserIdAndToUserId(fromUser.getId(), toUser.getId());
    }

    @Test
    public void testGetFollowList() {
        // given
        User loginUser = User.builder()
                .id(1L)
                .build();
        List<FollowInfoResponseDto> followingIdList = new ArrayList<>();
        followingIdList.add(new FollowInfoResponseDto());
        List<FollowInfoResponseDto> followerIdList = new ArrayList<>();
        followerIdList.add(new FollowInfoResponseDto());
        when(followRepository.findFollowingsByUserId(loginUser.getId())).thenReturn(followingIdList);
        when(followRepository.findFollowersByUserId(loginUser.getId())).thenReturn(followerIdList);

        // when
        FollowListResponseDto followListResponseDto = userService.getFollowList(loginUser);

        // then
        assertEquals(1, followListResponseDto.getFollowingCount());
        assertEquals(1, followListResponseDto.getFollowerCount());
        assertEquals(followingIdList, followListResponseDto.getFollowingIdList());
        assertEquals(followerIdList, followListResponseDto.getFollowerIdList());
    }

    @Test
    public void testGetUserInfo() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .email("test@example.com")
                .username("testuser")
                .profileUrl("profile.jpg")
                .address("Test Address")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        UserInfoResponse userInfoResponse = userService.getUserInfo(userId);

        // then
        assertEquals(userId, userInfoResponse.getId());
        assertEquals("test@example.com", userInfoResponse.getEmail());
        assertEquals("testuser", userInfoResponse.getUsername());
        assertEquals("profile.jpg", userInfoResponse.getImage());
        assertEquals("Test Address", userInfoResponse.getAddress());
        assertNotNull(userInfoResponse.getCreatedAt());
        assertNotNull(userInfoResponse.getUpdatedAt());
    }
}
//이렇게 작성하는게 맞나요..?
