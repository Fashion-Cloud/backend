package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.enums.Review;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostInfoResponseDto {

    @NotNull
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String title;

    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    private Double temperature;

    private Double windChill;

    private Review review;

    private SkyStatus skyStatus;

    private RainfallType rainfallType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static PostInfoResponseDto of(Post post) {
        return PostInfoResponseDto.builder()
                .id(post.getId())
                .email(post.getUser().getEmail())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .image(post.getImage())
                .temperature(post.getTemperature())
                .review(post.getReview())
                .skyStatus(post.getSkyStatus())
                .rainfallType(post.getRainfallType())
                .windChill(post.getWindChill())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}