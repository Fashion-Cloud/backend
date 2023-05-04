package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.mapper.PostMapper;
import com.techeer.fashioncloud.domain.post.dto.request.PostWeatherRequest;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.weather.exception.InvalidSkyCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    private PostService postService;

    @BeforeEach
    public void setUp() {
        postService = new PostService(postRepository, postMapper);
    }


    @Test
    @DisplayName("알맞지 않은 하늘 및 강수코드 입력시  InvalidCodeException 발생한다.")
    public void invalidCode() {
        //given
        PostWeatherRequest weather = new PostWeatherRequest(-1,0,1.0);

        //when, then
        Assertions.assertThrows(InvalidSkyCodeException.class, () -> postService.findPostsByWeather(weather));

    }
}