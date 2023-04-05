package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uuid2") // postgresql strategy 표기가 mysql과 차이가 있음.
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID userId = UUID.randomUUID(); // 임시 유저

    @NotNull
    private String name;

    @NotNull
    private String image;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Review review;

    @NotNull
    private Integer skyStatus;

    @NotNull
    private Double temperature;

    @NotNull
    private Double humidity;

    @NotNull
    private Integer rainfallType;

    @NotNull
    private Double windSpeed;

    @Builder
    public Post(UUID id, String name, String image, Review review, Integer skyStatus, Double temperature, Double humidity, Integer rainfallType, Double windSpeed) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.temperature = temperature;
        this.skyStatus = skyStatus;
        this.humidity = humidity;
        this.rainfallType = rainfallType;
        this.windSpeed = windSpeed;
        this.review = review;
    }

}