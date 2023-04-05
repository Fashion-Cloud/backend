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

    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL " +
            "AND p.skyStatus = :skyStatus " +
            "AND ABS(p.temperature - :temperature) <= 3 " +
            "AND ABS(p.humidity - :humidity) <= 5 " +
            "AND ABS(p.windSpeed - :windSpeed) <= 5.0 " +
            "AND p.rainfallType = :rainfallType ")
    List<Post> findByWeather(@Param("skyStatus") int skyStatus,
                             @Param("temperature") double temperature,
                             @Param("humidity") double humidity,
                             @Param("windSpeed") double windSpeed,
                             @Param("rainfallType") int rainfallType);
}