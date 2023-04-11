package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository repository;

    @PersistenceContext
    private EntityManager entityManager;


    public Post create(PostCreateServiceDto dto) {
        Post post = Post.builder()
                .name(dto.getName())
                .image(dto.getImage())
                .skyStatus(dto.getSkyStatus())
                .temperature(dto.getTemperature())
                .humidity(dto.getHumidity())
                .rainfallType(dto.getRainfallType())
                .windSpeed(dto.getWindSpeed())
                .review(dto.getReview())
                .build();
        Post savedPost = repository.save(post);
        entityManager.flush();
        return savedPost;
    }

    public List<PostResponseDto> findAllPosts() {
        return repository.findAll().stream()
                .map(PostResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Post findPostById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post ID"));
    }

    public void deleteRequestById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    public Post update(UUID id, PostUpdateRequestDto dto) {
        Post entity= postRepository.findById(id).get();
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setReview(dto.getReview());

        return entity;
    }
}