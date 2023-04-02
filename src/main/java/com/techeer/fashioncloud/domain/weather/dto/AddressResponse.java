package com.techeer.fashioncloud.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddressResponse {
    private String address;
}
