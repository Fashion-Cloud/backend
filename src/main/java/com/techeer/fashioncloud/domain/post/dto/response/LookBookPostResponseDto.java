package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LookBookPostResponseDto extends BaseEntity {

    @NotBlank
    private UUID id; // post id

    @NotBlank
    private LookBook lookBook;

    @NotBlank
    private Post post;
}