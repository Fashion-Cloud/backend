package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.domain.post.enums.Review;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

    @NotNull
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    @Column(length = 500)
    private String image;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Review review;

    @NotNull
    private Double temperature;

    @NotNull
    private SkyStatus skyStatus;

    @NotNull
    private RainfallType rainfallType;

    @Column(nullable = true)
    private Double windChill;

    @Builder
    public Post(UUID id, User user, String title, String image, Double temperature, Review review, SkyStatus skyStatus, RainfallType rainfallType, Double windChill) {
        this.id = id;
        this.user = user;
        this.image = image;
        this.title = title;
        this.temperature = temperature;
        this.skyStatus = skyStatus;
        this.rainfallType = rainfallType;
        this.review = review;
        this.windChill = windChill;
    }
}