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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<ResultResponse> create(
            @RequestBody PostCreateRequestDto dto
    ){
        Post entity = postService.create(postMapper.toServiceDto(dto));
        PostResponseDto response = postMapper.toResponseDto(entity);


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_CREATE_SUCCESS, response));

    }

    //현재 날씨 기반으로 비슷한 날씨의 post 리턴
    @GetMapping("/weather")
    public ResponseEntity<ResultResponse> getNowWeatherPosts(
            @RequestParam Integer skyCode,
            @RequestParam Integer rainfallCode,
            @RequestParam Double windChill
    ) {
        List<WeatherPostResponse> responseData = postService.findPostsByWeather(skyCode, rainfallCode, windChill);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, responseData));
    }


    @GetMapping
    public ResponseEntity<ResultResponse> getAllPosts() {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findAllPosts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getOnePost(@PathVariable UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_GET_SUCCESS, postService.findPostById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> delete(@PathVariable UUID id) {
        postService.deleteRequestById(id); // Post ID로 삭제
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_DELETE_SUCCESS));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponse> update (@PathVariable UUID id, @RequestBody PostUpdateRequestDto dto) {

        Post post=postService.update(id,dto);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_UPDATE_SUCCESS));
    }
}