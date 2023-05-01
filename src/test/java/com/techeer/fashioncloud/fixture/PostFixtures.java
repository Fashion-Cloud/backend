package com.techeer.fashioncloud.fixture;

import com.techeer.fashioncloud.domain.post.entity.Post;

import static com.techeer.fashioncloud.domain.post.entity.Review.더웠다;

public class PostFixtures {
    public static final Post UPLOAD_POST_FIXTURE =
            Post.builder()
                    .name("aaa")
                    .image("aaa")
                    .skyStatus(0)
                    .rainfallType(0)
                    .review(더웠다)
                    .build();
}