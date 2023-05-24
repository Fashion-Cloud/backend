package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.LookBookMapper;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.service.LookBookService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/looks")
@RequiredArgsConstructor
public class LookBookController {
    private final LookBookService lookBookService;
    private final LookBookMapper lookBookMapper;

    @PostMapping
    public ResponseEntity<ResultResponse> create(
            @RequestBody LookBookCreateRequestDto dto
    ){
        LookBook entity = lookBookService.create(dto);
        LookBookResponseDto response = lookBookMapper.toResponseDto(entity);


        return ResponseEntity.ok(ResultResponse.of(ResponseCode.POST_CREATE_SUCCESS, response));
    }
}