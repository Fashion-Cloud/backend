package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class S3UploadResponseDto extends BaseEntity {

    private String url;

}