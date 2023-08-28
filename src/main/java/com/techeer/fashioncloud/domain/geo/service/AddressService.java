package com.techeer.fashioncloud.domain.geo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.geo.constant.GeoConstant;
import com.techeer.fashioncloud.domain.geo.dto.AddressResponse;
import com.techeer.fashioncloud.domain.geo.dto.GeoApiRequest;
import com.techeer.fashioncloud.domain.geo.util.GeoApiCaller;
import com.techeer.fashioncloud.domain.geo.util.GeoApiParser;
import com.techeer.fashioncloud.global.domain.ExternalApiCallable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService implements ExternalApiCallable<AddressResponse> {

    private final GeoApiCaller geoApiCaller;

    public AddressResponse getAddress (Double latitude, Double longitude) {

        GeoApiRequest geoApiRequest = GeoApiRequest.builder()
                .path(GeoConstant.GEO_BASE_URL)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        return  geoApiCaller.get(geoApiRequest)
                .map(GeoApiParser::parse)
                .block();
    }

    public AddressResponse parseApiResponse(JsonNode jsonNode) {

        JsonNode documentsNode = jsonNode.get("documents");
        JsonNode regionNode = documentsNode.get(0);
        String fullAddress = regionNode.get("address_name").asText();
        String region1depth = regionNode.get("region_1depth_name").asText();
        String region2depth = regionNode.get("region_2depth_name").asText();
        String region3depth = regionNode.get("region_3depth_name").asText();

        return AddressResponse.builder()
                .fullAddress(fullAddress)
                .region1depth(region1depth)
                .region2depth(region2depth)
                .region3depth(region3depth)
                .build();
    }
}
