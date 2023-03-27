package com.techeer.fashioncloud.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class S3DeleteResponseDto {

    private String filename;

}