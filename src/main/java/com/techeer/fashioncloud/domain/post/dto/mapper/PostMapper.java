package com.techeer.fashioncloud.domain.post.dto.mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.entity.Post;

@Component
@RequiredArgsConstructor
public class PostMapper {


    public PostResponseDto toResponseDto(Post entity) {
        return PostResponseDto.builder()
                .id(entity.getId()) // ReviewId
                .build();
    }

    public PostCreateServiceDto toServiceDto(PostCreateRequestDto dto){
        return PostCreateServiceDto.builder()
                .name(dto.getName())
                .weatherCategory(dto.getWeatherCategory())
                .wearCategory(dto.getWearCategory())
                .image(dto.getImage())
                .build();
    }
}