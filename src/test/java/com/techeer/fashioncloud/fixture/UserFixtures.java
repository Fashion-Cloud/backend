package com.techeer.fashioncloud.fixture;


import com.techeer.fashioncloud.domain.auth.enums.ROLE;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;

import java.util.UUID;

public class UserFixtures {

        public static final User USER_FIXTURES =
                User.builder()
                        .id(1L)
                        .email("fashion@naver.com")
                        .password("fashion")
                        .username("username")
                        .profileUrl("imageUrl")
                        .address("address")
                        .role(ROLE.ADMIN)
                        .build();

        public static final User USER_FIXTURES_1 =
                User.builder()
                        .id(2L)
                        .email("fashion1@naver.com")
                        .password("fashion")
                        .username("username1")
                        .profileUrl("imageUrl")
                        .address("address")
                        .role(ROLE.ADMIN)
                        .build();


        public static final User USER_FIXTURES_2 =
                User.builder()
                        .id(3L)
                        .email("fashion2@naver.com")
                        .password("fashion")
                        .username("username2")
                        .profileUrl("imageUrl")
                        .address("address")
                        .role(ROLE.ADMIN)
                        .build();
}
