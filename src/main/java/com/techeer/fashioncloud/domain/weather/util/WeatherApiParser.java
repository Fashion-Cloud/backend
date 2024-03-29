package com.techeer.fashioncloud.domain.weather.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.external.WeatherApiResponse;
import com.techeer.fashioncloud.domain.weather.exception.WeatherApiException;

public class WeatherApiParser {

    private static final String NORMAL_SERVICE_CODE = "00";

    public static WeatherApiResponse parse(JsonNode apiResponse) {
        JsonNode header = apiResponse.get("response").get("header");
        String resultCode = header.get("resultCode").asText();
        String resultMsg = header.get("resultMsg").asText();

        if(!resultCode.equals(NORMAL_SERVICE_CODE)) {
            throw new WeatherApiException(resultCode, resultMsg);
        }

        JsonNode body = apiResponse.get("response").get("body");
        return WeatherApiResponse.builder()
                .resultCode(resultCode)
                .resultMsg(resultMsg)
                .pageNo(body.get("pageNo").intValue())
                .numOfRows(body.get("numOfRows").intValue())
                .totalCount(body.get("totalCount").intValue())
                .item(body.get("items").get("item"))
                .build();
    }
}
