package com.techeer.fashioncloud.domain.post.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder

public class LookBookPostDataResponseDto {

    private Long postId; // post id

    private Long userId; // 임시 유저

    @NotNull
    private String title;

    @NotNull
    private String image;

    @NotNull
    private Double temperature;
}
