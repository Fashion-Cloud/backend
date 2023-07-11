package com.techeer.fashioncloud.domain.post.dto.mapper;

import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostDataResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LookBookMapper {
    public LookBookResponseDto toBookResponseDto(LookBook entity) {
        return LookBookResponseDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .image(entity.getImage())
                .build();
    }

    public LookBookPostResponseDto toBookPostResponseDto(LookBookPost entity) {
        return LookBookPostResponseDto.builder()
                .id(entity.getId())
                .lookBook(entity.getLookBook())
                .post(entity.getPost())
                .build();
    }

    public LookBookPostDataResponseDto toDataDto(Post entity){
        return LookBookPostDataResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .userId(entity.getUserId())
                .temperature(entity.getTemperature())
                .build();
    }
}
