package com.techeer.fashioncloud.domain.post.repository;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<Post> findByUserId(Long userId);


    @Query("SELECT p FROM Post p LEFT JOIN LookBookPost lbp WHERE lbp.lookBook = :lookBook")
    List<Post> getLookBookPosts(@Param("lookBook") LookBook lookBook);

    @Query("SELECT p FROM Post p WHERE p.skyStatus IN :skyStatusList " +
            "AND p.rainfallType IN :rainfallTypeList " +
            "AND ABS(p.windChill - :windChill) <= 1 ")
    Page<Post> findPostsByWeather(
            @Param("skyStatusList") List<SkyStatus> skyStatusList,
            @Param("rainfallTypeList") List<RainfallType> rainfallTypeList,
            @Param("windChill") Double windChill,
            Pageable pageReqDto);
}