package com.techeer.fashioncloud.domain.post.repository;

import com.techeer.fashioncloud.domain.post.dto.request.NowWeatherRequest;
import com.techeer.fashioncloud.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * JpaRepository 상속 -> 자동으로 빈 등록 (@Repository 안 달아도 됨)
 * */

public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsById(UUID uuid);


    //soft delete 고려?
    Optional<List<Post>> findByWeather(NowWeatherRequest nowWeatherRequest);
}