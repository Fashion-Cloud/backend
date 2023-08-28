package com.techeer.fashioncloud.global.util.api.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;

@SuperBuilder
@Getter
public class WeatherApiRequest extends ExternalApiRequest {

    private HashMap<String, Object> queryParams;
}
