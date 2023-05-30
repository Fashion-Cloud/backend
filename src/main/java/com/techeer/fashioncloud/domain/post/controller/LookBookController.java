package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.LookBookMapper;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookPostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.service.LookBookService;
import com.techeer.fashioncloud.domain.post.service.PostService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class LookBookController {
    private final LookBookService bookService;
    private final PostService postService;
    private final LookBookMapper bookMapper;

    @PostMapping
    public ResponseEntity<ResultResponse> bookCreate(
            @RequestBody LookBookCreateRequestDto dto
    ){
        LookBook entity = bookService.bookCreate(dto);
        LookBookResponseDto response = bookMapper.toBookResponseDto(entity);


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_CREATE_SUCCESS, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getOneBook(@PathVariable UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_GET_SUCCESS, bookService.findBookById(id)));
    }

    @PostMapping("posts")
    public ResponseEntity<ResultResponse> lookBookCreate(
            @RequestBody LookBookPostCreateRequestDto dto
    ){
        LookBook lookBook = bookService.findBookById(dto.getLookBookId());
        Post post = postService.findPostById(dto.getPostId());

        LookBookPost entity = bookService.lookBookCreate(lookBook, post);
        LookBookPostResponseDto response = bookMapper.toBookPostResponseDto(entity);


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_CREATE_SUCCESS, response));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ResultResponse> getOneLookBook(@PathVariable UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_GET_SUCCESS, bookService.findLookBookById(id)));
    }

}