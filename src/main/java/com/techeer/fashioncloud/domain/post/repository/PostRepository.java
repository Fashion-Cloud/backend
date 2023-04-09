package com.techeer.fashioncloud.domain.post.repository;
import com.techeer.fashioncloud.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/*
 * JpaRepository 상속 -> 자동으로 빈 등록 (@Repository 안 달아도 됨)
 * */

public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsById(UUID uuid);
}