package com.techeer.fashioncloud.domain.weather.forecast;

import java.util.Map;

public interface WeatherApiCallable {
    Map<String, Object> getReqQueryParams();
}