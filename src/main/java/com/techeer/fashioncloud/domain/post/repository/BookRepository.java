package com.techeer.fashioncloud.domain.post.repository;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<LookBook, UUID> {
    List<LookBook> findByUserId(UUID userId);
}