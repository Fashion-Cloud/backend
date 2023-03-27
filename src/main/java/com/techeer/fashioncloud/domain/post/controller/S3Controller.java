package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.S3Mapper;
import com.techeer.fashioncloud.domain.post.dto.response.S3UploadResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.S3DeleteResponseDto;
import com.techeer.fashioncloud.domain.post.service.S3Service;
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

    private final S3Service service;
    private final S3Mapper mapper;

    @PostMapping
    public ResponseEntity<S3UploadResponseDto> uploadImage(
            @RequestBody MultipartFile image
    ) throws IOException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.toUploadResponseDto(service.uploadImage(image)));
    }

    @GetMapping
    public ResponseEntity<S3DeleteResponseDto> deleteImages(
            @RequestParam String filename
    ){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.toDeleteResponseDto(service.deleteImage(filename)));
    }
}