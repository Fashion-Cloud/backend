package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.auth.util.LoginUser;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookGetResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.service.LookBookService;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lookbooks")
@RequiredArgsConstructor
@Tag(name = "lookBook", description = "룩북 API")
public class LookBookController {
    private final LookBookService lookBookService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "룩북 표지 생성", description = "제목과 사진이 들어간 룩북표지를 생성합니다.")
    public ResponseEntity<ResultResponse> lookBookCreate(
            @LoginUser User loginUser,
            @RequestBody LookBookCreateRequestDto reqDto
    ) {
        LookBookResponseDto lookBookDto = lookBookService.lookBookCreate(loginUser, reqDto);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_CREATE_SUCCESS, lookBookDto));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "로그인한 사용자 룩북 리스트 불러오기", description = "로그인한 사용자 룩북 리스트를 불러온다.")
    public ResponseEntity<ResultResponse> getLookBookByUserId(
            @Parameter(name = "postId", description = "비필수: 미지정시 inLookBook필드 제공 X") @RequestParam(required = false) UUID postId,
            @LoginUser User loginUser
    ) {
        List<? extends LookBookResponseDto> lookBookDto = lookBookService.findUserLookBooks(loginUser, postId);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_GET_SUCCESS, lookBookDto));
    }

    @PostMapping("/{lookBookId}/posts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "룩북 포스트 업로드", description = "룩북에 게시글을 추가합니다")
    public ResponseEntity<ResultResponse> lookBookPostCreate(
            @LoginUser User loginUser,
            @Parameter(name = "lookBookId", description = "로그인한 유저의 룩북 ID") @PathVariable Long lookBookId,
            @Parameter(name = "postId", description = "추가할 게시글의 UUID") @RequestParam UUID postId
    ) {
        lookBookService.addPostToLookBook(loginUser.getId(), lookBookId, postId);

        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_POST_CREATE_SUCCESS));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "해당 룩북 불러오기", description = "id에 해당하는 룩북 게시글 포함하여 불러오기")
    public ResponseEntity<ResultResponse> getLookBookById(@Parameter(name = "id", description = "LookBookId") @PathVariable Long id) {

        LookBookGetResponseDto lookbook = lookBookService.getLookBookById(id);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.LOOK_BOOK_GET_SUCCESS, lookbook));
    }
}