package com.techeer.fashioncloud.domain.user.entity;

import com.techeer.fashioncloud.domain.auth.enums.ROLE;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")

@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id; //TODO: 타입 int로 변경

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

    @Column(length = 30, columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("USER")
    @NotNull
    @Column(length = 20)
    private ROLE role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> postList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LookBook> lookBookList;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> followingUserList; // 현재 사용자가 팔로우중

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Follow> followerUserList;  // 현재 사용자를 팔로우중

    public void setUserRole(ROLE role) {
        this.role = role;
    }

}