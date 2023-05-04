package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.techeer.fashioncloud.domain.post.entity.Review.추웠다;
import static com.techeer.fashioncloud.fixture.PostFixtures.POST_FIXTURES;
import static com.techeer.fashioncloud.fixture.PostFixtures.UPLOAD_POST_FIXTURES;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest extends BaseEntity {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    private Post givenPost;

    @BeforeEach
    @Transactional
    void setup(){ // 검색을 위한 setup
        givenPost = Post.builder()
                .id(UUID.fromString("0fb5a4a7-4743-4dc3-8a24-41cf9aef15ab"))
                .userId(UUID.fromString("eea26f3f-2457-49e7-9f69-3376ccdd72ee"))
                .name("AA")
                .image("aaa")
                .skyStatus(0)
                .rainfallType(3)
                .review(추웠다)
                .windChill(3.0)
                .build();

        lenient().when(postRepository.findById(any())).thenReturn(Optional.of(givenPost));
    }

    @Nested
    @DisplayName("Post 생성 테스트")
    class CreatePostTest {
        @Test
        @Transactional
        void createTest() {
            // when
            when(postRepository.save(any())).thenReturn(POST_FIXTURES);

            // then
            assertEquals(POST_FIXTURES, postService.create(UPLOAD_POST_FIXTURES));
        }
    }

    @Nested
    @DisplayName("Post 검색 테스트")
    class FindPostTest {
        @Test
        @Transactional
        void findByIdSuccessTest() {
            // when
            Post actualPost = postService.findPostById(givenPost.getId());

            // then
            assertEquals(givenPost.getId(), actualPost.getId());
            assertEquals(givenPost.getUserId(), actualPost.getUserId());
            assertEquals(givenPost.getName(), actualPost.getName());
            assertEquals(givenPost.getImage(), actualPost.getImage());
            assertEquals(givenPost.getReview(), actualPost.getReview());
            assertEquals(givenPost.getWindChill(), actualPost.getWindChill());
            assertEquals(givenPost.getSkyStatus(), actualPost.getSkyStatus());
            assertEquals(givenPost.getCreatedAt(), actualPost.getCreatedAt());
            assertEquals(givenPost.getUpdatedAt(), actualPost.getUpdatedAt());
        }
    }

    @Nested
    @DisplayName("Post 업데이트 테스트")
    class UpdatePostTest {

        @Test
        @Transactional
        void updatePostSuccessTest() {
            // given
            PostUpdateRequestDto givenDto = PostUpdateRequestDto.builder()
                    .name(POST_FIXTURES.getName())
                    .image(POST_FIXTURES.getImage())
                    .review(POST_FIXTURES.getReview())
                    .build();

            // when
            Post actualPost = postService.update(givenPost.getId(), givenDto);

            // then
            assertEquals(givenPost.getId(), actualPost.getId());
            assertEquals(givenPost.getUserId(), actualPost.getUserId());
            assertEquals(POST_FIXTURES.getName(), actualPost.getName());
            assertEquals(POST_FIXTURES.getImage(), actualPost.getImage());
            assertEquals(POST_FIXTURES.getReview(), actualPost.getReview());
            assertEquals(givenPost.getWindChill(), actualPost.getWindChill());
            assertEquals(givenPost.getSkyStatus(), actualPost.getSkyStatus());
            assertEquals(givenPost.getCreatedAt(), actualPost.getCreatedAt());
            assertEquals(givenPost.getUpdatedAt(), actualPost.getUpdatedAt());
        }
    }
}