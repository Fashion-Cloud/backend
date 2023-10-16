package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LookBookPostResponseDto extends LookBookResponseDto {

    @NotNull
    private boolean inLookbook;

    public static LookBookPostResponseDto of(LookBook lookBook, boolean isContained) {
        return LookBookPostResponseDto.builder()
                .id(lookBook.getId())
                .userId(lookBook.getUser().getId())
                .title(lookBook.getTitle())
                .image(lookBook.getImage())
                .inLookbook(isContained)
                .build();
    }
}