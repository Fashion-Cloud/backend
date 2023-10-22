package com.techeer.fashioncloud.domain.geo.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer id;

    private Integer parentRegionId;

    @NotNull
    private String fullName;

    @NotNull
    private String name;

    @Min(1)
    @Max(3)
    @NotNull
    @Column(columnDefinition = "smallint")
    private Integer depth;

    @NotNull
    @Column(columnDefinition = "decimal(10, 6)")
    private Double lat;

    @NotNull
    @Column(columnDefinition = "decimal(10, 6)")
    private Double lng;

    @NotNull
    @Column(columnDefinition = "smallint default 0")
    private Integer ordering;


}
