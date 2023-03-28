package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.UUID;
@Entity
@Getter
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE post SET deleted_at = CURRENT_TIMESTAMP where id = ?")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id = UUID.randomUUID();

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId = UUID.randomUUID(); // 임시 유저

    private String name;

    private Double temperature;

    private String image;

    @Enumerated(EnumType.STRING)
    private WeatherCategory weather;

    @Enumerated(EnumType.STRING)
    private WearCategory wear;

    @Builder
    public Post(UUID id, String name, String image, WeatherCategory weather, WearCategory wear, Double temperature) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.temperature = temperature;
        this.weather = weather;
        this.wear = wear;
    }

}