package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.mapper.PostMapper;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.entity.PostImage;
import com.techeer.fashioncloud.domain.post.exception.PostNotFoundException;
import com.techeer.fashioncloud.domain.post.repository.PostImageRepository;
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
    private final PostImageRepository postImageRepository;


    public Post create(PostCreateServiceDto dto) {
        Double windChill = calculator.getWindChill(dto.getTemperature(),dto.getWindSpeed());
        Post entity = postRepository.save(Post.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .image(dto.getImage())
                .skyStatus(dto.getSkyStatus())
                .rainfallType(dto.getRainfallType())
                .review(dto.getReview())
                .windChill(windChill)
                .build());

        // Save Images
        String postImage = dto.getImage();
        PostImage imageEntity = PostImage.builder()
                .post(entity)
                .url(postImage)
                .build();
        postImageRepository.save(imageEntity);
        
        return entity;
    }

    public List<WeatherPostResponse> findPostsByWeather(SkyStatus skyCode, RainfallType rainfallCode, Double windChill) {
        //TODO: 분기처리 개선
        List<Post> postEntityList = new ArrayList<>();

        //맑음
        if (skyCode == SkyStatus.CLEAR
                & rainfallCode == RainfallType.CLEAR) {
            postEntityList = postRepository.findNoRainfallPosts(windChill, SkyStatus.getClearCodeList(), RainfallType.getClearCodeList());
        }
        //흐림
        else if (SkyStatus.getCloudyCodeList().contains(skyCode)
                & rainfallCode == RainfallType.CLEAR) {
            postEntityList = postRepository.findNoRainfallPosts(windChill, SkyStatus.getCloudyCodeList(), RainfallType.getClearCodeList());
        }
        //비
        else if (RainfallType.getRainyCodeList().contains(rainfallCode)) {
            postEntityList = postRepository.findRainfallPosts(windChill, RainfallType.getRainyCodeList());
        }
        //눈
        else if (RainfallType.getSnowyCodeList().contains(rainfallCode)) {
            postEntityList = postRepository.findRainfallPosts(windChill, RainfallType.getSnowyCodeList());
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















