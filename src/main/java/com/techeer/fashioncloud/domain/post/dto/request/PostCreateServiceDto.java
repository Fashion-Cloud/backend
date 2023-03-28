package com.techeer.fashioncloud.domain.post.dto.request;

import com.techeer.fashioncloud.domain.post.entity.WeatherCategory;
import com.techeer.fashioncloud.domain.post.entity.WearCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateServiceDto {

    private UUID userId; // user 정보를 조회하여 PostRequest에 넣기 위함.

    private String name;

    private Double temperature;

    private String image;

    private WeatherCategory weatherCategory;

    private WearCategory wearCategory;

    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.
}