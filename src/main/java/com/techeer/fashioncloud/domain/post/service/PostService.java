package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.mapper.PostMapper;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostWeatherRequest;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.weather.constant.RainfallType;
import com.techeer.fashioncloud.domain.weather.constant.SkyStatus;
import com.techeer.fashioncloud.global.util.WindChillCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    
    @PersistenceContext
    private EntityManager entityManager;

    public Post create(PostCreateServiceDto dto) {
        Post post = Post.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .skyStatus(dto.getSkyStatus())
                .temperature(dto.getTemperature())
                .humidity(dto.getHumidity())
                .rainfallType(dto.getRainfallType())
                .windSpeed(dto.getWindSpeed())
                .review(dto.getReview())
                .build();
        Post savedPost = postRepository.save(post);
        entityManager.flush();
        return savedPost;
    }



    public List<WeatherPostResponse> findPostsByWeather(PostWeatherRequest weather) {

        List<Post> postEntityList = new ArrayList<>();
        //맑음
        if (weather.getSkyCode() == SkyStatus.CLEAR
                & weather.getRainfallCode() == RainfallType.CLEAR) {
            postEntityList = postRepository.findNoRainfallPosts(weather.getWindChill(), SkyStatus.clearCodeList, RainfallType.clearCodeList);
        }
        //흐림
        else if (SkyStatus.cloudyCodeList.contains(weather.getSkyCode())
                & weather.getRainfallCode() == RainfallType.CLEAR) {
            postEntityList = postRepository.findNoRainfallPosts(weather.getWindChill(), SkyStatus.cloudyCodeList, RainfallType.clearCodeList);
        }
        //비
        else if (RainfallType.RainyCodeList.contains(weather.getRainfallCode())) {
            postEntityList = postRepository.findRainfallPosts(weather.getWindChill(), RainfallType.RainyCodeList);
        }
        //눈
        else if (RainfallType.SnowyCodeList.contains(weather.getRainfallCode())) {
            postEntityList = postRepository.findRainfallPosts(weather.getWindChill(), RainfallType.SnowyCodeList);
        }
        else {
            throw new RuntimeException("날씨 정보 오류");
        }

        return postMapper.toPostDtoList(postEntityList);
    }
    
    
    public Post update(UUID id, PostUpdateRequestDto dto) {
        Post entity= postRepository.findById(id).get();
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setReview(dto.getReview());

        return entity;
    }
    
     public List<PostResponseDto> findAllPosts() {
     return repository.findAll().stream()
              .map(PostResponseDto::fromEntity)
              .collect(Collectors.toList());
    }

    public Post findPostById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post ID"));
    }

    public void deleteRequestById(UUID id) {
        repository.deleteById(id);
    }
}















