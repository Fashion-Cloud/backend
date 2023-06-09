package com.techeer.fashioncloud.domain.post.dto.request;

import com.techeer.fashioncloud.domain.post.entity.Review;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDto {

    @NotNull
    private UUID userId; // user 정보를 조회하여 PostRequest에 넣기 위함.

    @NotNull
    private String name;

    @NotNull
    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    @NotNull
    private Integer skyStatus;

    @NotNull
    private Double temperature;

    @NotNull
    private Integer rainfallType;

    @NotNull
    private Double windSpeed;

    @NotNull
    private Review review;
}