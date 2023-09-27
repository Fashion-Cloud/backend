package com.techeer.fashioncloud.domain.geo.entity;

import com.techeer.fashioncloud.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Region extends BaseEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uuid2") 
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    private int parentRegionId;

    @NotNull
    private String fullName;

    @NotNull
    private String name;

    @Min(1)
    @Max(3)
    @NotNull
    private int depth;

    @NotNull
    private float lat;

    @NotNull
    private float lng;

    private int ordering;




}
