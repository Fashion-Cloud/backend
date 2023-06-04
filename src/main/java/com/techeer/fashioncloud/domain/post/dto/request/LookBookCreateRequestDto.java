package com.techeer.fashioncloud.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookBookCreateRequestDto {

    @NotNull
    private UUID lookBookId;

    @NotNull
    private UUID postId;
}