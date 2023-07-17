package com.techeer.fashioncloud.domain.post.dto.request;


import com.techeer.fashioncloud.domain.post.entity.Review;
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

//    @NotBlank
//    private UUID userId; // user 정보를 조회하여 PostRequest에 넣기 위함.

    @NotNull
    private String name;

    @NotNull
    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.


    @NotNull
    private Review review;
}