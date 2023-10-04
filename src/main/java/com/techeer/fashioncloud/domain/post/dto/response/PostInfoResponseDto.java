package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostInfoResponseDto extends BaseEntity {

    @NotNull
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String title;

    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    public static PostInfoResponseDto of(Post post) {
        return PostInfoResponseDto.builder()
                .id(post.getId())
                .email(post.getUser().getEmail())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .image(post.getImage())
                .build();
    }
}