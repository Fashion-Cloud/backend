package com.techeer.fashioncloud.domain.post.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Setter
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "post_images")
public class PostImage extends BaseEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uuid2") // postgresql strategy 표기가 mysql과 차이가 있음.
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @ToString.Exclude
    private Post post;

    @NotNull
    private String url;

    public void update(String url) {
        this.url = url;
    }
}