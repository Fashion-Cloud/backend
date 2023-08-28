package com.techeer.fashioncloud.domain.geo.dto;

import com.techeer.fashioncloud.global.util.api.dto.ExternalApiRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class GeoApiRequest extends ExternalApiRequest {

    private Double latitude;
    private Double longitude;
}
