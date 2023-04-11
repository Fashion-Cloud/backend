package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.PostMapper;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostWeatherRequest;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.service.PostService;
import com.techeer.fashioncloud.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.techeer.fashioncloud.global.dto.ApiResponse.ok;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
    private final PostMapper mapper;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(
            @RequestBody PostCreateRequestDto dto
    ){
        Post entity = service.create(mapper.toServiceDto(dto));
        PostResponseDto response = mapper.toResponseDto(entity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //현재 날씨 기반으로 비슷한 날씨의 post 리턴
    @GetMapping("/weather")
    public ApiResponse getNowWeatherPosts(
            @RequestBody PostWeatherRequest postWeatherRequest
    ) {
        return ok(service.findPostsByWeather(postWeatherRequest));
    }


    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getOnePost(@PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteRequestById(id); // Post ID로 삭제
        return ResponseEntity
                .noContent()
                .build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PostResponseDto> update
//            (
//                    @PathVariable UUID id, @RequestBody PostUpdateRequestDto dto)
//    {
//
//        Post entity = service.update( id,dto);
//        // PostUpdateRequestDto response = mapper.toUpdateServiceDto(entity);
//        return ResponseEntity
//                .ok(service.findPostById(id));
//    }
}