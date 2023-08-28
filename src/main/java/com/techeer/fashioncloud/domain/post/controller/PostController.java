package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.PostMapper;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.service.PostService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "post", description = "게시물 API")
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    @Operation(summary = "게시물 생성", description ="오늘 날씨에 맞는 게시물을 생성한다.")
    public ResponseEntity<ResultResponse> create(
            @RequestBody PostCreateRequestDto dto
    ){
        Post entity = postService.create(postMapper.toServiceDto(dto));
        PostResponseDto response = postMapper.toResponseDto(entity);


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_CREATE_SUCCESS, response));
    }

    //현재 날씨 기반으로 비슷한 날씨의 post 리턴
    @GetMapping("/weather")
    @Operation(summary = "날씨 반환", description ="실시간 날씨를 반환한다.")
    public ResponseEntity<ResultResponse> getNowWeatherPosts(
            @Parameter(name="skyCode") @RequestParam Integer skyCode,
            @Parameter(name="rainfallCode")  @RequestParam Integer rainfallCode,
            @Parameter(name="windChill")  @RequestParam Double windChill
    ) {
        List<WeatherPostResponse> responseData = postService.findPostsByWeather(skyCode, rainfallCode, windChill);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, responseData));
    }

    @GetMapping
    @Operation(summary = "게시물 전체 조회", description = "페이지네이션을 통해 10개씩 게시물을 반환한다.")
    public ResponseEntity<ResultResponse> getAllPosts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "createdAt") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort.split(",")));
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.pageList(pageable)));
    }


    @GetMapping("/{id}")
    @Operation(summary = "id로 게시물 조회", description ="postId를 통해 게시물을 조회한다.")
    public ResponseEntity<ResultResponse> getOnePost(@Parameter(name="id",description = "PostId")@PathVariable UUID id) {


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findPostById(id)));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "userId로 게시물 조회", description ="userId를 통해 게시물을 조회한다.")
    public ResponseEntity<ResultResponse> getPostByUserId( @Parameter(name="id",description = "UserId")@PathVariable("id") UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findPostByUserId(id)));
    }

    @DeleteMapping("/{id}")
    //@Operation(summary = "게시물 삭제", description ="postId를 통해 게시물을 삭제한다.")
    public ResponseEntity<ResultResponse> delete(@Parameter(name="id",description = "PostId")@PathVariable("id") UUID id) {
        postService.deleteRequestById(id); // Post ID로 삭제
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_DELETE_SUCCESS));

    }

    @PutMapping("/{id}")
    @Operation(summary = "게시물 수정", description ="postId를 통해 게시물을 수정한다.")
    public ResponseEntity<ResultResponse> update (@Parameter(name="id",description = "PostId") UUID id, @RequestBody PostUpdateRequestDto dto) {

        Post post=postService.update(id,dto);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_UPDATE_SUCCESS));
    }
}