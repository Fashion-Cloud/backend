package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Internal;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID userId = UUID.randomUUID(); // 임시 유저

    private String name;

    private String image;

    @Enumerated(EnumType.STRING)
    private WeatherCategory weather;

    @Enumerated(EnumType.STRING)
    private WearCategory wear;

    private Integer skyStatus;

    private Double temperature;

    private Double humidity;

    private Integer rainfallType;

    private Double windSpeed;

    @Builder
    public Post(UUID id, String name, String image, WeatherCategory weather, WearCategory wear, Integer skyStatus, Double temperature, Double humidity, Integer rainfallType, Double windSpeed) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.temperature = temperature;
        this.skyStatus = skyStatus;
        this.humidity = humidity;
        this.rainfallType = rainfallType;
        this.windSpeed = windSpeed;
        this.wear = wear;
        this.weather = weather;
    }

}