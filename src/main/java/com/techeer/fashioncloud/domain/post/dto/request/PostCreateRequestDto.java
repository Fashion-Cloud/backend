package com.techeer.fashioncloud.domain.post.dto.request;
import com.techeer.fashioncloud.domain.post.entity.Review;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private UUID userId; // user 정보를 조회하여 PostRequest에 넣기 위함.

    @NotBlank
    private String name;

    @NotBlank
    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    @NotBlank
    private Integer skyStatus;

    @NotBlank
    private Double temperature;

    @NotBlank
    private Double humidity;

    @NotBlank
    private Integer rainfallType;

    @NotBlank
    private Double windSpeed;

    @NotBlank
    private Review review;
}