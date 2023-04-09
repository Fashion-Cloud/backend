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

import java.util.ArrayList;
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
                .windChill(WindChillCalculator.getWindChill(dto.getTemperature(), dto.getWindSpeed()))
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
}
