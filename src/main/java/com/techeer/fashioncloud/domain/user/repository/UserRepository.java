package com.techeer.fashioncloud.domain.user.repository;

import com.techeer.fashioncloud.domain.user.dto.response.FollowInfoResponseDto;
import com.techeer.fashioncloud.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.isDeleted=true where u.id=:id")
    void deleteUser(@Param("id") Long id);


}
