package com.techeer.fashioncloud.domain.post.dto.mapper;

import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {


    public PostResponseDto toResponseDto(Post entity) {
        return PostResponseDto.builder()
                .id(entity.getId()) // PostId
                .userId(entity.getUserId())
                .name(entity.getName())
                .image(entity.getImage())
                .build();
    }

    public PostCreateServiceDto toServiceDto(PostCreateRequestDto dto){
        return PostCreateServiceDto.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .skyStatus((dto.getSkyStatus()))
                .temperature(dto.getTemperature())
                .rainfallType(dto.getRainfallType())
                .windSpeed(dto.getWindSpeed())
                .review(dto.getReview())
                .build();
    }

    public WeatherPostResponse toPostDto(Post post) {
        return WeatherPostResponse.builder()
                .id(post.getId())
                .name(post.getName())
                .imageUrl(post.getImage())
                .build();
    }

    public List<WeatherPostResponse> toPostDtoList(List<Post> postEntityList) {
        return postEntityList.stream()
                .map(p -> toPostDto(p))
                .collect(Collectors.toList());
    }
}