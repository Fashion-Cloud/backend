package com.techeer.fashioncloud.fixture;

import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.enums.Review;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;

import java.util.UUID;

import static com.techeer.fashioncloud.fixture.UserFixtures.USER_FIXTURES;


public class PostFixtures {
    public static final Post POST_FIXTURES =
            Post.builder()
                    .id(UUID.randomUUID())
                    .user(USER_FIXTURES)
                    .image("aaa")
                    .review(Review.CHILLY)
                    .temperature(10.0)
                    .skyStatus(SkyStatus.CLOUDY)
                    .windChill(3.0)
                    .build();
    public static final Post POST_FIXTURES_1 =
            Post.builder()
                    .id(UUID.randomUUID())
                    .user(USER_FIXTURES)
                    .image("aaa1")
                    .review(Review.CHILLY)
                    .temperature(10.0)
                    .skyStatus(SkyStatus.CLOUDY)
                    .windChill(3.0)
                    .build();
    public static final Post POST_FIXTURES_2 =
            Post.builder()
                    .id(UUID.randomUUID())
                    .user(USER_FIXTURES)
                    .image("aaa2")
                    .review(Review.CHILLY)
                    .temperature(10.0)
                    .skyStatus(SkyStatus.CLOUDY)
                    .windChill(3.0)
                    .build();
//    public static final PostCreateServiceDto UPLOAD_POST_FIXTURES =
//           PostCreateServiceDto.builder()
//                    .userId(UUID.randomUUID())
//                    .name("AA")
//                   .image("aaa")
//                    .skyStatus(SkyStatus.CLEAR)
//                    .temperature(3.0)
//                    .rainfallType(RainfallType.CLEAR)
//                    .windSpeed(7.0)
//                    .review(추웠다)
//                    .build();
}