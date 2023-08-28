package com.techeer.fashioncloud.domain.geo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.geo.dto.AddressResponse;

public class GeoApiParser {

    public static AddressResponse parse(JsonNode apiResponse) {
        Integer totalCount = apiResponse.get("meta").get("total_count").intValue();

        if(totalCount < 1) throw new RuntimeException(); //TODO: 익셉션핸들링, kakao api 상태코드별 핸들링

        JsonNode data = apiResponse.get("documents").get(0);
        return AddressResponse.builder()
                .fullAddress(data.get("address_name").asText())
                .region1depth(data.get("region_1depth_name").asText())
                .region2depth(data.get("region_2depth_name").asText())
                .region3depth(data.get("region_3depth_name").asText())
                .build();
    }
}
