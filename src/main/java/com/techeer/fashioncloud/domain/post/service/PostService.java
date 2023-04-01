package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateServiceDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
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
                .build());

        return entity;
    }

    @Transactional
    public void deleteRequestById(UUID id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public List<Post> findRequestById(UUID id) { // ID로 검색
        return postRepository.findAll();
    }
}
