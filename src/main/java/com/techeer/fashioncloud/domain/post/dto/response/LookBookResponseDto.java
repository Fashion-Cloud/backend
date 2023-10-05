package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LookBookResponseDto {

    @NotNull
    private Long id; //

    @NotNull
    private Long userId;

    @NotNull
    private String title;

    @NotNull
    private String image;

    public static LookBookResponseDto of(LookBook lookBook) {
        return LookBookResponseDto.builder()
                .id(lookBook.getId())
                .userId(lookBook.getUser().getId())
                .title(lookBook.getTitle())
                .image(lookBook.getImage())
                .build();
    }
}