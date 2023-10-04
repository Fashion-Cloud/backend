package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "lookbooks")
public class LookBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String image;

}