package com.techeer.fashioncloud.domain.user.repository;

import com.techeer.fashioncloud.domain.user.entity.Follow;
import com.techeer.fashioncloud.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByToUser(User user); // 팔로워 수

    Long countByFromUser(User user); // 팔로잉 수

    int countByFromUserIdAndToUserId(Long fromUserId, Long toUserId); // 팔로우 되어있는지 확인

    void deleteByFromUserIdAndToUserId(Long fromUserId, Long toUserId); // 언팔로우
}