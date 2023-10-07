package com.techeer.fashioncloud.domain.post.repository;

import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LookBookPostRepository extends JpaRepository<LookBookPost, UUID> {

}