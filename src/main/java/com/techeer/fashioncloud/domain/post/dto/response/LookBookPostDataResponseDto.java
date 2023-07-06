package com.techeer.fashioncloud.domain.post.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder

public class LookBookPostDataResponseDto {

    private UUID id; // post id

    private UUID userId; // 임시 유저

    @NotBlank
    private String name;

    @NotBlank
    private String image;



}
