package com.techeer.fashioncloud.domain.user.entity;

import com.techeer.fashioncloud.domain.auth.ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

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
    private ROLE role;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> followingUserList; // 현재 사용자가 팔로우중

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> followerUserList;  // 현재 사용자를 팔로우중

    public void setUserRole(ROLE role) {
        this.role = role;
    }
}