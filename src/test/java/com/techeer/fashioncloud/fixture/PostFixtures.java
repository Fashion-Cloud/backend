package com.techeer.fashioncloud.fixture;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.entity.Post;

import java.util.UUID;

import static com.techeer.fashioncloud.domain.post.entity.Review.추웠다;

public class PostFixtures {
    public static final Post POST_FIXTURES =
            Post.builder()
                    .userId(UUID.randomUUID())
                    .name("AA")
                    .image("aaa")
                    .skyStatus(0)
                    .rainfallType(3)
                    .review(추웠다)
                    .windChill(3.0)
                    .build();
    public static final PostCreateServiceDto UPLOAD_POST_FIXTURES =
            PostCreateServiceDto.builder()
            .userId(UUID.randomUUID())
            .name("AA")
            .image("aaa")
            .skyStatus(0)
            .temperature(3.0)
            .rainfallType(3)
            .windSpeed(7.0)
            .review(추웠다)
            .build();
}