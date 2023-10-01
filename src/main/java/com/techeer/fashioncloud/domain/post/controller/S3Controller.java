package com.techeer.fashioncloud.domain.post.controller;

import com.techeer.fashioncloud.domain.post.dto.mapper.S3Mapper;
import com.techeer.fashioncloud.domain.post.service.S3Service;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@Tag(name = "s3", description = "이미지 API")
public class S3Controller {

    private final S3Service s3Service;
    private final S3Mapper s3Mapper;


    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "이미지 업로드", description = "이미지를 업로드하면 url을 반환합니다.")
    public ResponseEntity<ResultResponse> uploadImage
            (@Parameter(
                    name = "image",
                    description = "Files to be uploaded",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            ) @RequestParam("image") @RequestBody MultipartFile image
            ) throws IOException {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultResponse.of(
                        ResponseCode.IMAGE_UPLOAD_SUCCESS, s3Mapper
                                .toUploadResponseDto(s3Service.uploadImage(image))));
    }

    @GetMapping
    @Operation(summary = "이미지 삭제", description = "업로드한 이미지를 삭제합니다.")
    public ResponseEntity<ResultResponse> deleteImages(
            @Parameter(
                    name = "filename",
                    description = "filename to be deleted"
            ) @RequestParam String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultResponse.of(
                        ResponseCode.IMAGE_DELETE_SUCCESS,
                        s3Mapper.toDeleteResponseDto(s3Service.deleteImage(filename))));

    }
}
