package com.techeer.fashioncloud.global.entity;

import jakarta.persistence.*;

@Entity
public class WeatherCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memo;

    private Integer value;
}