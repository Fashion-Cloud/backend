package com.techeer.fashioncloud.domain.user.entity;

import com.techeer.fashioncloud.domain.auth.AuthEnums;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true)
    @NotNull
    private String email;

    @Column(length = 100)
    @NotNull
    private String password;

    @Column(length = 50)
    @NotNull
    private String username;

    @Column(length = 100)
    private String profileUrl;

    @Column(length = 100)
    private String address;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("USER")
    @NotNull
    @Column(length = 20)
    private AuthEnums.ROLE role;

    public void setUserRole(AuthEnums.ROLE role) {
        this.role = role;
    }
}