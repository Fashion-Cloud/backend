package com.techeer.fashioncloud.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class WeatherPostResponse {
    private UUID id;
    private String name;
    private String imageUrl;
}