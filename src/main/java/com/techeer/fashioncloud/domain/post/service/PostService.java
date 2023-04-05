package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.mapper.PostMapper;
import com.techeer.fashioncloud.domain.post.dto.request.NowWeatherRequest;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Post create(PostCreateServiceDto dto) {

        // Save Request
        Post entity = postRepository.save(Post.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .skyStatus((dto.getSkyStatus()))
                .temperature((dto.getTemperature()))
                .humidity((dto.getHumidity()))
                .rainfallType((dto.getRainfallType()))
                .windSpeed((dto.getWindSpeed()))
                .review((dto.getReview()))
                .build());

        return entity;
    }

    @Transactional
    public void deleteRequestById(UUID id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public List<Post> findAll() { // ID로 검색
        return postRepository.findAll(); // DTO를 거치지 않은 순수 데이터
    }

    @Transactional(readOnly = true)
    public PostResponseDto findRequestById(UUID id) { // PostID로 검색
        Post entity = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException(("해당 게시글이 없습니다. id="+id))); // DTO를 거치고 나온 데이터
        return new PostResponseDto(entity);
    }

    public List<WeatherPostResponse> findSunnyPosts(NowWeatherRequest nowWeatherRequest) {
        List<Post> postEntityList = postRepository.findSunnyPosts(
                nowWeatherRequest.getTemperature(),
                nowWeatherRequest.getHumidity(),
                nowWeatherRequest.getWindSpeed()
                );
        List<WeatherPostResponse> postDtoList = postMapper.toPostDtoList(postEntityList);
            return postDtoList;
    }

    public List<WeatherPostResponse> findCloudyPosts(NowWeatherRequest nowWeatherRequest) {
        List<Post> postEntityList = postRepository.findCloudyPosts(
                nowWeatherRequest.getTemperature(),
                nowWeatherRequest.getHumidity(),
                nowWeatherRequest.getWindSpeed()
        );
        List<WeatherPostResponse> postDtoList = postMapper.toPostDtoList(postEntityList);
        return postDtoList;
    }

    public List<WeatherPostResponse> findSnowyPosts(NowWeatherRequest nowWeatherRequest) {
        List<Post> postEntityList = postRepository.findSnowyPosts(
                nowWeatherRequest.getTemperature(),
                nowWeatherRequest.getHumidity(),
                nowWeatherRequest.getWindSpeed()
        );
        List<WeatherPostResponse> postDtoList = postMapper.toPostDtoList(postEntityList);
        return postDtoList;
    }

    public List<WeatherPostResponse> findRainyPosts(NowWeatherRequest nowWeatherRequest) {
        List<Post> postEntityList = postRepository.findRainyPosts(
                nowWeatherRequest.getTemperature(),
                nowWeatherRequest.getHumidity(),
                nowWeatherRequest.getWindSpeed()
        );
        List<WeatherPostResponse> postDtoList = postMapper.toPostDtoList(postEntityList);
        return postDtoList;
    }
}
