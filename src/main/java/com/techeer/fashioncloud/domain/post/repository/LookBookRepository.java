package com.techeer.fashioncloud.domain.post.repository;

import com.techeer.fashioncloud.domain.post.entity.LookBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LookBookRepository extends JpaRepository<LookBook, Long> {
    List<LookBook> findByUserId(Long userId);
}