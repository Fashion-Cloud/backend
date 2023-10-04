package com.techeer.fashioncloud.domain.post.dto.request;


import com.techeer.fashioncloud.domain.post.enums.Review;
import com.techeer.fashioncloud.global.util.validation.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String image;

    @NotNull
    @ValidEnum(enumClass = Review.class)
    private Review review;
}