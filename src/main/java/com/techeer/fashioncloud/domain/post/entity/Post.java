package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.domain.weather.constant.RainfallType;
import com.techeer.fashioncloud.domain.weather.constant.SkyStatus;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.UUID;
@Entity
@Getter
@Setter
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
    private UUID userId;

    @NotNull
    private String name;

    @NotNull
    private String image;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Review review;

    @NotNull
    private SkyStatus skyStatus;

    @NotNull
    private RainfallType rainfallType;


    // TODO: 디폴트값은 개발용으로 넣어둔것. 이후 제거
    @Column(nullable = true, columnDefinition = "DOUBLE PRECISION DEFAULT 11.5")
    private Double windChill;

    @Builder
    public Post(UUID id, UUID userId, String name, String image, Review review, SkyStatus skyStatus, RainfallType rainfallType, Double windChill) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.image = image;
        this.skyStatus = skyStatus;
        this.rainfallType = rainfallType;
        this.review = review;
        this.windChill = windChill;
    }
}