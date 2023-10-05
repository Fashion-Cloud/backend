package com.techeer.fashioncloud.domain.geo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private String fullAddress;
    private String region1depth;
    private String region2depth;
    private String region3depth;
}
