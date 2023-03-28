package com.techeer.fashioncloud.domain.post.dto.response;
import com.techeer.fashioncloud.global.entity.BaseEntity;
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

}