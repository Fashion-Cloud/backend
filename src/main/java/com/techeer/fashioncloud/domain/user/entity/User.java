package com.techeer.fashioncloud.domain.user.entity;

import com.techeer.fashioncloud.domain.auth.AuthEnums;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length =100)
    private String profileUrl;

    @Column(length =100)
    private String address;

    @Column(length =50, unique = true)
    @NotNull
    private String nickname;

    @Column(length =50, unique = true)
    @NotNull
    private String email;

    @Column(length =100)
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
//    @ColumnDefault("USER")
    @NotNull
    private AuthEnums.ROLE role;

    public void setUserRole(AuthEnums.ROLE role) {
        this.role = role;
    }
}