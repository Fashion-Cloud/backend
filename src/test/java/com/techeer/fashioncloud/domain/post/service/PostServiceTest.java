package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.techeer.fashioncloud.fixture.PostFixtures.POST_FIXTURES;
import static com.techeer.fashioncloud.fixture.PostFixtures.UPLOAD_POST_FIXTURES;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest extends BaseEntity {
// Flush DB after each test

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("REVIEW 생성 테스트")
    void createTest() {
        // when
        when(postRepository.save(any())).thenReturn(POST_FIXTURES);

        // then
        assertEquals(POST_FIXTURES,
                postService.create(UPLOAD_POST_FIXTURES));
    }
}