package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
    @Column(length = 500)
    private String image;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Review review;

    @NotNull
    private Double temperature;

    @NotNull
    private Integer skyStatus;

    @NotNull
    private Integer rainfallType;


    // TODO: 디폴트값은 개발용으로 넣어둔것. 이후 제거
    @Column(nullable = true, columnDefinition = "DOUBLE PRECISION DEFAULT 11.5")
    private Double windChill;

    @Builder
    public Post(UUID id, UUID userId, String name, String image, Double temperature, Review review, Integer skyStatus, Integer rainfallType, Double windChill) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.image = image;
        this.temperature = temperature;
        this.skyStatus = skyStatus;
        this.rainfallType = rainfallType;
        this.review = review;
        this.windChill = windChill;
    }
}