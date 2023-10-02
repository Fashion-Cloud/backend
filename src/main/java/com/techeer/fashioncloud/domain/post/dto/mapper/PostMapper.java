//package com.techeer.fashioncloud.domain.post.dto.mapper;
//
//import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
//import com.techeer.fashioncloud.domain.post.dto.response.PostCreateResponseDto;
//import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
//import com.techeer.fashioncloud.domain.post.entity.Post;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class PostMapper {
//
//
//    public PostCreateResponseDto toResponseDto(Post entity) {
//        return PostCreateResponseDto.builder()
//                .id(entity.getId()) // PostId
//                .userId(entity.getUserId())
//                .name(entity.getName())
//                .image(entity.getImage())
//                .build();
//    }
//
//    public PostCreateServiceDto toServiceDto(Long userId, PostCreateRequestDto dto) {
//        return PostCreateServiceDto.builder()
//                .userId(userId)
//                .name(dto.getName())
//                .image(dto.getImage())
//                .skyStatus((dto.getSkyStatus()))
//                .temperature(dto.getTemperature())
//                .rainfallType(dto.getRainfallType())
//                .windSpeed(dto.getWindSpeed())
//                .review(dto.getReview())
//                .build();
//    }
//
//
//    public Page<PostCreateResponseDto> toDtoPageList(Page<Post> postList) {
//        return postList.map(this::toResponseDto);
//    }
//
//    public WeatherPostResponse toPostDto(Post post) {
//        return WeatherPostResponse.builder()
//                .id(post.getId())
//                .name(post.getName())
//                .imageUrl(post.getImage())
//                .build();
//    }
//
//    public List<WeatherPostResponse> toPostDtoList(List<Post> postEntityList) {
//        return postEntityList.stream()
//                .map(p -> toPostDto(p))
//                .collect(Collectors.toList());
//    }
//}