package com.techeer.fashioncloud.domain.post.repository;
import com.techeer.fashioncloud.domain.post.dto.response.PostResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/*
 * JpaRepository 상속 -> 자동으로 빈 등록 (@Repository 안 달아도 됨)
 * */

public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsById(UUID uuid);
}