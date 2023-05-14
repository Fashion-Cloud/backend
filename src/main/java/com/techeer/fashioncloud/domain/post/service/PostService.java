package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.mapper.PostMapper;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.exception.PostNotFoundException;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.weather.constant.RainfallType;
import com.techeer.fashioncloud.domain.weather.constant.SkyStatus;
import com.techeer.fashioncloud.global.util.WindChillCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    @Autowired
    private final WindChillCalculator calculator;
    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public Post create(PostCreateServiceDto dto) {
        Double windChill = calculator.getWindChill(dto.getTemperature(),dto.getWindSpeed());
        Post post = Post.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .image(dto.getImage())
                .skyStatus(dto.getSkyStatus())
                .rainfallType(dto.getRainfallType())
                .review(dto.getReview())
                .windChill(windChill)
                .build();
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public List<WeatherPostResponse> findPostsByWeather(Integer skyCode, Integer rainfallCode, Double windChill) {
        //TODO: 분기처리 개선
        List<Post> postEntityList = new ArrayList<>();

        //맑음
        if (skyCode == SkyStatus.CLEAR
                & rainfallCode == RainfallType.CLEAR) {
            throw new PostNotFoundException();
//            postEntityList = postRepository.findNoRainfallPosts(weather.getWindChill(), SkyStatus.clearCodeList, RainfallType.clearCodeList);
        }
        //흐림
        else if (SkyStatus.cloudyCodeList.contains(skyCode)
                & rainfallCode == RainfallType.CLEAR) {
            postEntityList = postRepository.findNoRainfallPosts(windChill, SkyStatus.cloudyCodeList, RainfallType.clearCodeList);
        }
        //비
        else if (RainfallType.RainyCodeList.contains(rainfallCode)) {
            postEntityList = postRepository.findRainfallPosts(windChill, RainfallType.RainyCodeList);
        }
        //눈
        else if (RainfallType.SnowyCodeList.contains(rainfallCode)) {
            postEntityList = postRepository.findRainfallPosts(windChill, RainfallType.SnowyCodeList);
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
     return postRepository.findAll().stream()
              .map(PostResponseDto::fromEntity)
              .collect(Collectors.toList());
    }

    public Post findPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(()-> new PostNotFoundException());
    }

    public void deleteRequestById(UUID id) {
        if(!postRepository.existsById(id)){
            throw new PostNotFoundException();
        }
        postRepository.deleteById(id);
    }
}















