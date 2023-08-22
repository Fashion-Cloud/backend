package com.techeer.fashioncloud.domain.weather.forecast;

import com.fasterxml.jackson.databind.JsonNode;

public interface ApiParsable<T> {
    public T parseWeatherInfo(JsonNode jsonNode);
}
