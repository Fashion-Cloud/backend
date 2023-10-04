package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.auth.util.LoginUser;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostCreateResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostInfoResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
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

import java.util.List;
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
            @RequestBody PostCreateRequestDto reqDto,
            @LoginUser User loginUser
    ) {
        PostCreateResponseDto resDto = postService.create(loginUser, reqDto);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_CREATE_SUCCESS, resDto));
    }

    // TODO 페이지네이션
    @GetMapping("/weather")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "날씨에 따른 게시글 목록 조회", description = "날씨가 비슷한 지역의 게시글 목록을 반환한다")
    public ResponseEntity<ResultResponse> getNowWeatherPosts(
            // TODO: ModelAttribute
            @Parameter(name = "skyCode", description = "기상청 하늘상태 코드") @RequestParam Integer skyCode,
            @Parameter(name = "rainfallCode", description = "기상청 강수상태 코드") @RequestParam Integer rainfallCode,
            @Parameter(name = "windChill", description = "체감온도") @RequestParam Double windChill
    ) {
        List<WeatherPostResponse> responseData = postService.getPostsByWeather(skyCode, rainfallCode, windChill);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, responseData));
    }

    // TODO 페이지네이션, 마무리
    @GetMapping("/follow/timeline")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "팔로우 사용자 타임라인 조회", description = "내가 팔로우하는 사용자의 타임라인을 조회한다")
    public ResponseEntity<ResultResponse> getNowWeatherPosts(
    ) {
//        List<WeatherPostResponse> responseData = postService.getFollowTimeline();
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "게시물 전체 조회", description = "조건에 따라 게시물 전체를 조회한다")
    public ResponseEntity<ResultResponse> getAllPosts(
            @ModelAttribute @ParameterObject CustomPageRequest pageReqDto) {

        PaginatedResponse<PostInfoResponseDto> paginatedPosts = postService.getPostPages(pageReqDto.of());
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, paginatedPosts));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "id로 게시물 조회", description = "postId를 통해 게시물을 조회한다.")
    public ResponseEntity<ResultResponse> getPost(
            @Parameter(name = "id", description = "PostId")
            @PathVariable UUID id) {

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findPostById(id)));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "userId로 게시물 조회", description = "userId를 통해 게시물을 조회한다.")
    public ResponseEntity<ResultResponse> getPostByUserId(@Parameter(name = "id", description = "UserId") @PathVariable("id") UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findPostByUserId(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "게시물 삭제", description = "postId를 통해 게시물을 삭제한다.")
    public ResponseEntity<ResultResponse> delete(
            @Parameter(name = "id", description = "PostId")
            @PathVariable("id") UUID id) {
        postService.deletePostById(id);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_DELETE_SUCCESS));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "게시물 수정", description = "postId를 통해 게시물을 수정한다.")
    public ResponseEntity<ResultResponse> update(
            @Parameter(name = "id", description = "PostId") UUID id,
            @RequestBody PostUpdateRequestDto dto) {

        postService.update(id, dto);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_UPDATE_SUCCESS));
    }
}