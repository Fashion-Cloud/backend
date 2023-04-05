package com.techeer.fashioncloud.domain.post.dto.response;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.entity.Review;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto extends BaseEntity {

    private UUID id; // post id

    private UUID userId; // 임시 유저

    @NotBlank
    private String name;

    @NotBlank
    private String image;
    // S3 API를 이용하여 Image를 먼저 S3에 올린 후에 반환된 URL을 저장함.

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.name = entity.getName();
        this.image = entity.getImage();
    }
}