package com.techeer.fashioncloud.domain.weather.dto.external;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiResponse {

    private String resultCode;
    private String resultMsg;
    private Integer pageNo;
    private Integer numOfRows;
    private Integer totalCount;
    private JsonNode item;

}
