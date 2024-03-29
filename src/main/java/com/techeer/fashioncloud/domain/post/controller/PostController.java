package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.auth.util.LoginUser;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostGetRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostCreateResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostInfoResponseDto;
import com.techeer.fashioncloud.domain.post.service.PostService;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.global.dto.CustomPageRequest;
import com.techeer.fashioncloud.global.dto.PaginatedResponse;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "post", description = "게시물 API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "게시물 생성", description = "오늘 날씨에 맞는 게시물을 생성한다.")
    public ResponseEntity<ResultResponse> create(
            @Valid @RequestBody PostCreateRequestDto reqDto,
            @LoginUser User loginUser
    ) {
        PostCreateResponseDto resDto = postService.create(loginUser, reqDto);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_CREATE_SUCCESS, resDto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "게시물 전체 조회", description = "조건에 따라 게시물 전체를 조회한다")
    public ResponseEntity<ResultResponse> getAllPosts(
            @ParameterObject @ModelAttribute CustomPageRequest pageReqDto
    ) {
        PaginatedResponse<PostInfoResponseDto> paginatedPosts = postService.getPosts(pageReqDto.set());
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, paginatedPosts));
    }

    @GetMapping("/weather")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "날씨에 따른 게시글 목록 조회", description = "날씨가 비슷한 지역의 게시글 목록을 반환한다")
    public ResponseEntity<ResultResponse> getNowWeatherPosts(
            @ParameterObject @ModelAttribute PostGetRequestDto reqDto,
            @ParameterObject @ModelAttribute CustomPageRequest pageReqDto
    ) {
        PaginatedResponse<PostInfoResponseDto> responseData = postService.getPostsByWeather(pageReqDto.set(), reqDto);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, responseData));
    }

    @GetMapping("/follow/timeline")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "팔로우 사용자 타임라인 조회", description = "내가 팔로우하는 사용자의 타임라인을 조회한다")
    public ResponseEntity<ResultResponse> getFollowTimeline(
            @ParameterObject @ModelAttribute CustomPageRequest pageReqDto,
            @LoginUser User loginUser
    ) {
        PaginatedResponse<PostInfoResponseDto> timeline = postService.getFollowTimeline(loginUser, pageReqDto.set());
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, timeline));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "id로 게시물 조회", description = "postId를 통해 게시물을 조회한다.")
    public ResponseEntity<ResultResponse> getPost(
            @Parameter(name = "id", description = "PostId")
            @PathVariable UUID id,
            @LoginUser User loginUser
    ) {
        postService.updateViewCount(loginUser, id);
        postService.findPostById(id);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findPostById(id)));
    }

    @GetMapping("/users/{id}")
    @Operation(summary = "userId로 게시물 조회", description = "userId를 통해 게시물을 조회한다.")
    public ResponseEntity<ResultResponse> getPostByUserId(@Parameter(name = "id", description = "UserId") @PathVariable("id") Long id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findPostByUserId(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "게시물 삭제", description = "postId를 통해 게시물을 삭제한다.")
    public ResponseEntity<ResultResponse> delete(
            @Parameter(name = "id", description = "PostId")
            @PathVariable("id") UUID id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "게시물 수정", description = "postId를 통해 게시물을 수정한다.")
    public ResponseEntity<ResultResponse> update(
            @Parameter(name = "id", description = "PostId") @PathVariable("id") UUID id,
            @Valid @RequestBody PostUpdateRequestDto reqDto) {

        PostInfoResponseDto updatedPost = PostInfoResponseDto.of(postService.update(id, reqDto));

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_UPDATE_SUCCESS, updatedPost));
    }
}