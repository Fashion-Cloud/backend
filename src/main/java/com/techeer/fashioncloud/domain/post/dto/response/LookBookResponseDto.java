package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LookBookResponseDto extends BaseEntity {

    @NotNull
    private UUID id; // post id

    @NotNull
    private String title;

    @NotNull
    private String image;

}