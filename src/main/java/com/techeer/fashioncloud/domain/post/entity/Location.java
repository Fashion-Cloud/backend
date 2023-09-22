package com.techeer.fashioncloud.domain.post.entity;

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
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 ID 필드
    @Column(length = 36, nullable = false, updatable = false)
    private Long id;

    @NotNull
    private String latitude; // 위도

    @NotNull
    private String longitude; // 경도

    @NotNull
    private String address; // 주소

    @NotNull
    private String weatherAreaCode; // 기상특보 구역 코드
}
