package com.techeer.fashioncloud.global.util.api.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.LinkedMultiValueMap;

@SuperBuilder
@Getter
public class GeoApiRequest extends ExternalApiRequest {
    private String headerName;
    private String[] headerValues;
    private LinkedMultiValueMap<String, String> queryParams;
}
