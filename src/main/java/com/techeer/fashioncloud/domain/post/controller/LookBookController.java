package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.LookBookMapper;
import com.techeer.fashioncloud.domain.post.dto.request.BookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.BookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Book;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
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
            @RequestBody BookCreateRequestDto dto
    ){
        Book entity = bookService.bookCreate(dto);
        BookResponseDto response = bookMapper.toBookResponseDto(entity);


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.BOOK_CREATE_SUCCESS, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getOneBook(@PathVariable UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.BOOK_GET_SUCCESS, bookService.findBookById(id)));
    }

    @PostMapping("posts")
    public ResponseEntity<ResultResponse> lookBookCreate(
            @RequestBody LookBookCreateRequestDto dto
    ){
        Book lookBook = bookService.findBookById(dto.getLookBookId());
        Post post = postService.findPostById(dto.getPostId());

        LookBook entity = bookService.lookBookCreate(lookBook, post);
        LookBookResponseDto response = bookMapper.toBookPostResponseDto(entity);


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_CREATE_SUCCESS, response));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ResultResponse> getOneLookBook(@PathVariable UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_GET_SUCCESS, bookService.findLookBookById(id)));
    }

}