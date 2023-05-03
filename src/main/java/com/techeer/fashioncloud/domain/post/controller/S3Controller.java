package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.S3Mapper;
import com.techeer.fashioncloud.domain.post.dto.response.S3UploadResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.S3DeleteResponseDto;
import com.techeer.fashioncloud.domain.post.service.S3Service;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;
    private final S3Mapper s3Mapper;


    @PostMapping
    public ResponseEntity<ResultResponse> uploadImage(
            @RequestBody MultipartFile image
    ) throws IOException {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultResponse.of(
                        ResponseCode.IMAGE_UPLOAD_SUCCESS, s3Mapper
                                .toUploadResponseDto(s3Service.uploadImage(image))));
    }

    @GetMapping
    public ResponseEntity<ResultResponse> deleteImages(
            @RequestParam String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultResponse.of(
                        ResponseCode.IMAGE_DELETE_SUCCESS,
                        s3Mapper.toDeleteResponseDto(s3Service.deleteImage(filename))));

    }
}
