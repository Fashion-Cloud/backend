package com.techeer.fashioncloud.domain.user.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;



@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private User fromUser;

    public User getToUser() {
        return toUser;
    }

    public User getFromUser() {
        return fromUser;
    }
}