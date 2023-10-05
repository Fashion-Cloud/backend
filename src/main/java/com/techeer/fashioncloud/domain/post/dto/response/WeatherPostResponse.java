package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherPostResponse {
    private UUID id;
    private String title;
    private String imageUrl;

    public static WeatherPostResponse of(Post post) {
        return WeatherPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .imageUrl(post.getImage())
                .build();
    }
}