package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(PostCreateServiceDto dto) {

        // Save Request
        Post entity = postRepository.save(Post.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .skyStatus((dto.getSkyStatus()))
                .temperature((dto.getTemperature()))
                .humidity((dto.getHumidity()))
                .rainfallType((dto.getRainfallType()))
                .windSpeed((dto.getWindSpeed()))
                .review((dto.getReview()))
                .build());

        return entity;
    }

    public Post findRequestById(UUID id) { // PostID로 검색
        return postRepository.findById(id).orElseThrow(()->new IllegalArgumentException(("해당 게시글이 없습니다. id="+id))); // DTO를 거치고 나온 데이터
    }

    public List<PostResponseDto> findAllPosts() {
        List<Post> posts = postRepository.findAll(); // 리스트 형식으로 전체 불러오기
        return posts.stream().map(PostResponseDto::fromEntity).collect(Collectors.toList()); // map함수를 통해 하나하나 담기
    }

    public void deleteRequestById(UUID id) {
        postRepository.deleteById(id);
    }
}
