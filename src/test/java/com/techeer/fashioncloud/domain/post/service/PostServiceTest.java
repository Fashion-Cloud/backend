package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.entity.Review;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.techeer.fashioncloud.domain.post.entity.Review.더웠다;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
// Flush DB after each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostServiceTest {

    // 시간 관계상 Repository Test Code 작성하지 않아 Mocking도 하지 않음.
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    private UUID givenUser;
    private String givenName;
    private String givenImage;
    private Integer givenSkyStatus;
    private Double givenTemperature;
    private Integer givenRainFallType;
    private Double givenWindSpeed;
    private Review givenReview;

    @BeforeEach
    void setUp() {
        givenUser = UUID.fromString("f9ad3dee-0782-40ad-a513-0f9e8a26ed84");
        givenName = "aa";
        givenTemperature = 3.0;
        givenImage = "aaa";
        givenSkyStatus = 0;
        givenReview = 더웠다;
        givenRainFallType = 3;
        givenWindSpeed = 7.0;

        postRepository.flush();
    }

    @Nested
    @DisplayName("REVIEW 생성 테스트")
    class PostCreateTest {

        @Test
        @DisplayName("성공")
        void createTest() {
            // given
            PostCreateServiceDto givenDto = PostCreateServiceDto.builder()
                    .userId(givenUser)
                    .name(givenName)
                    .image(givenImage)
                    .skyStatus(givenSkyStatus)
                    .rainfallType(givenRainFallType)
                    .review(givenReview)
                    .build();

            // when
            Post actualPost = postService.create(givenDto);

            // then
            assertThat(actualPost.getUserId()).isEqualTo(givenUser);
            assertThat(actualPost.getName()).isEqualTo(givenName);
            assertThat(actualPost.getImage()).isEqualTo(givenImage);
            assertThat(actualPost.getSkyStatus()).isEqualTo(givenSkyStatus);
            assertThat(actualPost.getRainfallType()).isEqualTo(givenRainFallType);
            assertThat(actualPost.getReview()).isEqualTo(givenReview);
            assertThat(actualPost.getWindChill()).isEqualTo(0.0);
        }
    }
}