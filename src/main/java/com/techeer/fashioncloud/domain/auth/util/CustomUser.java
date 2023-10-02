package com.techeer.fashioncloud.domain.auth.util;

import com.techeer.fashioncloud.domain.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Getter
public class CustomUser extends org.springframework.security.core.userdetails.User {

    public final User user;

    public CustomUser(User user) {
        super(user.getEmail(), user.getPassword(), Set.of(new SimpleGrantedAuthority(user.getRole().getRole())));
        this.user = user;
    }
}