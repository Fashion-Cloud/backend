package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostCreateResponseDto {

    @NotNull
    private UUID id; // post id

    private String email;

    private String username;

    private String title;

    private String image;

    private Double temperature;

    private Double windChill;

    private SkyStatus skyStatus;

    private RainfallType rainfallType;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static PostCreateResponseDto of(Post post, User user) {
        return PostCreateResponseDto.builder()
                .id(post.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .title(post.getTitle())
                .image(post.getImage())
                .temperature(post.getTemperature())
                .skyStatus(post.getSkyStatus())
                .rainfallType(post.getRainfallType())
                .windChill(post.getWindChill())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}