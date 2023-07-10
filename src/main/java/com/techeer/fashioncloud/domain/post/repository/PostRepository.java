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

    // 맑거나 흐림 - 하늘상태, 강수형태, 체감온도 필터링
    @Query("SELECT p FROM Post p WHERE p.skyStatus IN :skyCodeList " +
            "AND p.rainfallType IN :rainfallCodeList " +
            "AND ABS(p.windChill - :windChill) <= 1 ")
    List<Post> findNoRainfallPosts(
            @Param("windChill") Double windChill,
            @Param("skyCodeList") List<Integer> skyCodeList,
            @Param("rainfallCodeList") List<Integer> rainfallCodeList);

    //눈 혹은 비 - 강수형태와 체감온도로만 필터링
    @Query("SELECT p FROM Post p WHERE p.rainfallType IN :rainfallCodeList " +
            "AND ABS(p.windChill - :windChill) <= 1 ")
    List<Post> findRainfallPosts(
            @Param("windChill") Double windChill,
            @Param("rainfallCodeList") List<Integer> rainfallCodeList);

}