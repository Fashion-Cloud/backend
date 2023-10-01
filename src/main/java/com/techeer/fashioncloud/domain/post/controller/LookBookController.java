package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.LookBookMapper;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookPostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.service.LookBookService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "lookBook", description = "룩북 API")
public class LookBookController {
    private final LookBookService bookService;
    private final LookBookMapper bookMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "룩북 표지 생성", description = "제목과 사진이 들어간 룩북표지를 생성합니다.")
    public ResponseEntity<ResultResponse> lookBookCreate(
            @RequestBody LookBookCreateRequestDto dto
    ){
        LookBook entity = bookService.lookBookCreate(dto);
        LookBookResponseDto response = bookMapper.toBookResponseDto(entity);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_CREATE_SUCCESS, response));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "유저 아이디 이용해 룩북 불러오기", description = "유저 아이디를 이용해 해당 룩북 불러온다.")
    public ResponseEntity<ResultResponse> getLookBookByUser(@Parameter(name="userId")@PathVariable UUID userId) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_GET_SUCCESS, bookService.findBookByUserId(userId)));
    }


    @PostMapping("posts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "룩북 포스트 업로드", description = "포스트와 룩북과 연결을 연결합니다.")
    public ResponseEntity<ResultResponse> lookBookPostCreate(
            @RequestBody LookBookPostCreateRequestDto dto
    ){
        LookBookPost entity = bookService.lookBookPostCreate(dto);
        LookBookPostResponseDto response = bookMapper.toBookPostResponseDto(entity);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_POST_CREATE_SUCCESS, response));
    }

    @GetMapping("/posts/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "해당 룩북 불러오기", description = "id에 해당하는 룩북 불러오기")
    public ResponseEntity<ResultResponse> getOneLookBook(@Parameter(name="id",description = "LookBookId")@PathVariable UUID id) {
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_GET_SUCCESS, bookService.findLookBookById(id)));
    }

}