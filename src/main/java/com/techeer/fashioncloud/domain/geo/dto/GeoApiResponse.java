package com.techeer.fashioncloud.domain.geo.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoApiResponse {

    private Integer totalCount;
    private JsonNode documents;
    private String address;
    private String region1DepthName;
    private String region2DepthName;
    private String region3DepthName;
    private String region4DepthName;
    private String regionCode;

}
