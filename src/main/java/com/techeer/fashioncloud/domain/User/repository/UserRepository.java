package com.techeer.fashioncloud.domain.User.repository;


import java.util.Optional;

import com.techeer.fashioncloud.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}