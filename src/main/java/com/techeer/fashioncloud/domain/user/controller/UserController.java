package com.techeer.fashioncloud.domain.user.controller;

import com.techeer.fashioncloud.domain.user.dto.response.FollowListResponseDto;
import com.techeer.fashioncloud.domain.user.service.UserService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "user", description = "유저 API")
public class UserController {
    private final UserService userService;

    @PostMapping("/follow/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "유저 팔로우", description = "userId를 통해 유저를 팔로우한다")
    public ResponseEntity<ResultResponse> follow(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails loginUser
    ) {
        userService.follow(loginUser, id);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.USER_FOLLOW_SUCCESS));
    }

    @PostMapping("/unfollow/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "유저 언팔로우", description = "userId를 통해 유저를 언팔로우한다")
    public ResponseEntity<ResultResponse> unfollow(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails loginUser
    ) {
        userService.unfollow(loginUser, id);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.USER_UNFOLLOW_SUCCESS));
    }

    @GetMapping("/follow")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "팔로우/팔로잉 사용자 목록 조회", description = "로그인한 사용자의 팔로우/팔로잉 사용자 목록을 조회한다")
    public ResponseEntity<ResultResponse> follow(
            @AuthenticationPrincipal UserDetails loginUser
    ) {
        FollowListResponseDto followListResDto = userService.getFollowList(loginUser);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.USER_FOLLOW_LIST_GET_SUCCESS, followListResDto));
    }
}