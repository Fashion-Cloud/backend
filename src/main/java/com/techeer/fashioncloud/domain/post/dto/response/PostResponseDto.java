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
public class PostResponseDto extends BaseEntity {

    @NotNull
    private UUID id; // post id

    @NotNull
    private UUID userId; // 임시 유저

    @NotNull
    private String name;

    @NotNull
    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    public static PostResponseDto fromEntity(Post entity) {
        return PostResponseDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .image(entity.getImage())
                .build();
    }
}