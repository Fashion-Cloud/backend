package com.techeer.fashioncloud.domain.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class AuthEnums {
    @Getter
    public enum ROLE {
        ROLE_USER("USER"),
        ROLE_ADMIN("ADMIN");

        private String role;

        ROLE(String role) {
            this.role = role;
        }
    }
}