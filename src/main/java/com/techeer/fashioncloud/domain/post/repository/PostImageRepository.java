package com.techeer.fashioncloud.domain.post.repository;

import com.techeer.fashioncloud.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}