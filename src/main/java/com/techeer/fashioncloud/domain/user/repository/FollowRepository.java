package com.techeer.fashioncloud.domain.user.repository;

import com.techeer.fashioncloud.domain.user.dto.response.FollowInfoResponseDto;
import com.techeer.fashioncloud.domain.user.entity.Follow;
import com.techeer.fashioncloud.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FollowRepository extends JpaRepository<Follow, Long> {
    Long countByToUser(User user); // 팔로워 수

    Long countByFromUser(User user); // 팔로잉 수

    int countByFromUserIdAndToUserId(Long fromUserId, Long toUserId); // 팔로우 되어있는지 확인

    void deleteByFromUserIdAndToUserId(Long fromUserId, Long toUserId); // 언팔로우

    @Query("SELECT new com.techeer.fashioncloud.domain.user.dto.response.FollowInfoResponseDto(f.toUser.id, f.toUser.email, f.toUser.profileUrl, f.toUser.username) FROM Follow f WHERE f.fromUser.id = ?1")
    List<FollowInfoResponseDto> findFollowingsByUserId(Long fromUserId);

    @Query("SELECT new com.techeer.fashioncloud.domain.user.dto.response.FollowInfoResponseDto(f.fromUser.id, f.fromUser.email, f.fromUser.profileUrl, f.fromUser.username) FROM Follow f WHERE f.toUser.id = ?1")
    List<FollowInfoResponseDto> findFollowersByUserId(Long toUserId);
}