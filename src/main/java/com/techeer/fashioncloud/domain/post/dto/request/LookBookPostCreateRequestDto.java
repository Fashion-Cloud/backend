package com.techeer.fashioncloud.domain.post.dto.request;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.Post;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookBookPostCreateRequestDto {

    @NotNull
    private LookBook lookBook;

    @NotNull
    private Post post;
}