package com.techeer.fashioncloud.domain.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public enum ROLE {

    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    ROLE(String role) {
        this.role = role;
    }
}