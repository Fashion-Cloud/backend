package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostCreateResponseDto extends BaseEntity {

    @NotNull
    private UUID id; // post id

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String title;

    @NotNull
    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    public static PostCreateResponseDto of(Post post, User user) {
        return PostCreateResponseDto.builder()
                .id(post.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .title(post.getTitle())
                .image(post.getImage())
                .build();
    }
}