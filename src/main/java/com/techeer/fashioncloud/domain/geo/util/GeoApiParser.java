package com.techeer.fashioncloud.domain.geo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.geo.dto.AddressResponse;
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;

public class GeoApiParser {

    public static AddressResponse parse(JsonNode apiResponse) {
        Integer totalCount = apiResponse.get("meta").get("total_count").intValue();

        if(totalCount < 1) throw new BusinessException(ErrorCode.ADRRESS_NOT_FOUND);

        JsonNode data = apiResponse.get("documents").get(0);
        return AddressResponse.builder()
                .fullAddress(data.get("address_name").asText())
                .region1depth(data.get("region_1depth_name").asText())
                .region2depth(data.get("region_2depth_name").asText())
                .region3depth(data.get("region_3depth_name").asText())
                .build();
    }
}
