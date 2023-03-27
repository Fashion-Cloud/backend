package com.techeer.fashioncloud.domain.post.dto.mapper;

import com.techeer.fashioncloud.domain.post.dto.response.S3UploadResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.S3DeleteResponseDto;
import org.springframework.stereotype.Component;

@Component
public class S3Mapper {

    public S3UploadResponseDto toUploadResponseDto(String url){
        return S3UploadResponseDto.builder()
                .url(url)
                .build();
    }

    public S3DeleteResponseDto toDeleteResponseDto(String filename){
        return S3DeleteResponseDto.builder()
                .filename(filename)
                .build();
    }
}