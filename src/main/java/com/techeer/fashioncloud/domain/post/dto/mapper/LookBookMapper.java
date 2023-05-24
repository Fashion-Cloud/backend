package com.techeer.fashioncloud.domain.post.dto.mapper;

import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LookBookMapper {
    public LookBookResponseDto toResponseDto(LookBook entity) {
        return LookBookResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }
}
