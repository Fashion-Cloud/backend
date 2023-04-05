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

    // 날씨가 맑음, 강수형태 없음
    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL " +
            "AND p.skyStatus = 0 " +
            "AND ABS(p.temperature - :temperature) <= 3 " +
            "AND ABS(p.humidity - :humidity) <= 5 " +
            "AND ABS(p.windSpeed - :windSpeed) <= 5 " +
            "AND p.rainfallType = 0 ")
    List<Post> findSunnyPosts(
                              @Param("temperature") double temperature,
                              @Param("humidity") double humidity,
                              @Param("windSpeed") double windSpeed);

    // 날씨가 구름 많음 or 흐림 + 강수 없음
    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL " +
            "AND (p.skyStatus = 3 OR p.skyStatus = 4) " +
            "AND ABS(p.temperature - :temperature) <= 3 " +
            "AND ABS(p.humidity - :humidity) <= 5 " +
            "AND ABS(p.windSpeed - :windSpeed) <= 5 " +
            "AND p.rainfallType = 0 ")
    List<Post> findCloudyPosts(@Param("temperature") double temperature,
                               @Param("humidity") double humidity,
                               @Param("windSpeed") double windSpeed);

    // 강수형태 눈 + 비/눈 + 빗방울눈날림 + 눈날림
    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL " +
            "AND ABS(p.temperature - :temperature) <= 3 " +
            "AND ABS(p.humidity - :humidity) <= 5 " +
            "AND ABS(p.windSpeed - :windSpeed) <= 5 " +
            "AND (p.rainfallType = 2 OR p.rainfallType = 3 OR p.rainfallType = 6 OR p.rainfallType = 7 )")
    List<Post> findSnowyPosts(@Param("temperature") double temperature,
                              @Param("humidity") double humidity,
                              @Param("windSpeed") double windSpeed);

    // 강수형태 비 + 비/눈 + 빗방울눈날림 + 빗방울
    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL " +
            "AND ABS(p.temperature - :temperature) <= 3 " +
            "AND ABS(p.humidity - :humidity) <= 5 " +
            "AND ABS(p.windSpeed - :windSpeed) <= 5 " +
            "AND (p.rainfallType = 1 OR p.rainfallType = 2 OR p.rainfallType = 5 OR p.rainfallType = 6 )")
    List<Post> findRainyPosts(@Param("temperature") double temperature,
                              @Param("humidity") double humidity,
                              @Param("windSpeed") double windSpeed);
}