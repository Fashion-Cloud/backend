package com.techeer.fashioncloud.domain.post.repository;

import com.techeer.fashioncloud.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/*
 * JpaRepository 상속 -> 자동으로 빈 등록 (@Repository 안 달아도 됨)
 * */

public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsById(UUID uuid);

    List<Post> findByUserId(UUID userId);

    @Query("SELECT p FROM Post p WHERE p.skyStatus IN :skyCodes " +
            "AND p.rainfallType IN :rainfallCodes " +
            "AND ABS(p.windChill - :windChill) <= 1 ")
    List<Post> findPostsByWeather(
            @Param("skyCodes") List<Integer> skyCodeList,
            @Param("rainfallCodes") List<Integer> rainfallCodeList,
            @Param("windChill") Double windChill);
}