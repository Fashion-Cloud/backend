package com.techeer.fashioncloud.domain.post.dto.response;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String name;

    @NotNull
    private String image;

    @NotNull
    private  Double temperature;


}
