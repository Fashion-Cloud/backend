package com.techeer.fashioncloud.domain.post.dto.request;

import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.enums.Review;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import com.techeer.fashioncloud.domain.weather.util.WindChillCalculator;
import com.techeer.fashioncloud.global.util.validation.ValidEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    @ValidEnum(enumClass = SkyStatus.class)
    private SkyStatus skyStatus;

    @NotNull
    @ValidEnum(enumClass = RainfallType.class)
    private RainfallType rainfallType;

    @NotNull
    private Double temperature;

    @NotNull
    private Double windSpeed;

    @ValidEnum(enumClass = Review.class)
    private Review review;


    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .image(image)
                .temperature(temperature)
                .skyStatus(skyStatus)
                .rainfallType(rainfallType)
                .review(review)
                .windChill(WindChillCalculator.getWindChill(temperature, windSpeed))
                .build();
    }
}