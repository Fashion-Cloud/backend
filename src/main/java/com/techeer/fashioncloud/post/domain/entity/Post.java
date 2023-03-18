package com.techeer.fashioncloud.post.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP where id = ?")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    
    @Enumerated(EnumType.STRING)
    private PostCategory weather;

    private WearCategory wear;

    private String img_url;

    private Double temperature;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //삭제 시점
    private LocalDateTime deletedAt;

    @Builder
    public Post(Long id, String img_url, PostCategory weather, WearCategory wear, Double temperature) {
        this.id = id;
        this.img_url = img_url;
        this.temperature = temperature;
        this.weather = weather;
        this.wear = wear;
    }

}