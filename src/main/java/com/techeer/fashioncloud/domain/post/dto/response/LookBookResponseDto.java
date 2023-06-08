package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LookBookResponseDto extends BaseEntity {

    @NotBlank
    private UUID id; // post id

    @NotBlank
    private String title;

}