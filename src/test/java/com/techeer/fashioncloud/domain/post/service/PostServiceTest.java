package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.entity.Review;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.techeer.fashioncloud.domain.post.entity.Review.추웠다;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// Flush DB after each test
public class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    private Integer givenId;
    private UUID givenUser;
    private String givenName;
    private String givenImage;
    private Integer givenSkyStatus;
    private Double givenTemperature;
    private Integer givenRainFallType;
    private Double givenWindSpeed;
    private Review givenReview;

    @BeforeEach
    @Transactional
    void setUp() {
        givenUser = UUID.randomUUID();
        givenName = "aa";
        givenTemperature = 3.0;
        givenImage = "aaa";
        givenSkyStatus = 0;
        givenReview = 추웠다;
        givenRainFallType = 3;
        givenWindSpeed = 7.0;
    }

    @Nested
    @DisplayName("REVIEW 생성 테스트")
    class PostCreateTest extends BaseEntity {

        @Test
        @DisplayName("성공")
        void createTest(){
            // given
            PostCreateServiceDto givenDto = PostCreateServiceDto.builder()
                    .userId(givenUser)
                    .name(givenName)
                    .image(givenImage)
                    .skyStatus(givenSkyStatus)
                    .temperature(givenTemperature)
                    .rainfallType(givenRainFallType)
                    .windSpeed(givenWindSpeed)
                    .review(givenReview)
                    .build();

            // when
            Post actualPost = postService.create(givenDto);

            // then
            then(actualPost.getUserId()).isEqualTo(givenUser);
            then(actualPost.getName()).isEqualTo(givenName);
            then(actualPost.getImage()).isEqualTo(givenImage);
            then(actualPost.getReview()).isEqualTo(givenReview);
            then(actualPost.getSkyStatus()).isEqualTo(givenSkyStatus);
            then(actualPost.getRainfallType()).isEqualTo(givenRainFallType);
            then(actualPost.getWindChill()).isEqualTo(givenWindSpeed);
        }
    }
}