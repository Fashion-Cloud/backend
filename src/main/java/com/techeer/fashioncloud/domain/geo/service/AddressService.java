package com.techeer.fashioncloud.domain.geo.service;

import com.techeer.fashioncloud.domain.geo.RegionRepository;
import com.techeer.fashioncloud.domain.geo.entity.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final RegionRepository regionRepository;

    public String getAddress(Double latitude, Double longitude) {
        Region location = regionRepository.findNearestLocation(latitude, longitude)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 대한 주소를 찾을 수 없습니다."));

        return location.getFullName();
    }

//    private final GeoApiCaller geoApiCaller;
//
//    public AddressResponse getAddress (Double latitude, Double longitude) {
//
//        GeoApiRequest geoApiRequest = GeoApiRequest.builder()
//                .path(GeoConstant.GEO_BASE_URL)
//                .latitude(latitude)
//                .longitude(longitude)
//                .build();
//
//        return  geoApiCaller.get(geoApiRequest)
//                .map(GeoApiParser::parse)
//                .block();
//    }
//
//    public AddressResponse parseApiResponse(JsonNode jsonNode) {
//
//        JsonNode documentsNode = jsonNode.get("documents");
//        JsonNode regionNode = documentsNode.get(0);
//        String fullAddress = regionNode.get("address_name").asText();
//        String region1depth = regionNode.get("region_1depth_name").asText();
//        String region2depth = regionNode.get("region_2depth_name").asText();
//        String region3depth = regionNode.get("region_3depth_name").asText();
//
//        return AddressResponse.builder()
//                .fullAddress(fullAddress)
//                .region1depth(region1depth)
//                .region2depth(region2depth)
//                .region3depth(region3depth)
//                .build();
//    }
}
