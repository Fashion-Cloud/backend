package com.techeer.fashioncloud.domain.post.dto.response;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookBookGetResponseDto {

    private String title;

    private String image;

    private Long userId;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<PostInfoResponseDto> posts;


    public static LookBookGetResponseDto of(LookBook lookBook, List<Post> posts) {
        return LookBookGetResponseDto.builder()
                .title(lookBook.getTitle())
                .image(lookBook.getImage())
                .userId(lookBook.getUser().getId())
                .username(lookBook.getUser().getUsername())
                .createdAt(lookBook.getCreatedAt())
                .updatedAt(lookBook.getUpdatedAt())
                .posts(posts.stream().map(PostInfoResponseDto::of).toList()).build();
    }
}

