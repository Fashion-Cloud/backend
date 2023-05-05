package com.techeer.fashioncloud.domain.weather.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.ForecastResponse;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 단기예보
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VilageFcst extends Forecast{

    private Coordinate coordinate;

    //TODO: 단기에보 BaseTime지정
    @Override
    public String setBaseTime() {
        return null;
    }

    @Override
    public ForecastResponse parseWeatherInfo(JsonNode jsonNode) {
        return null;
    }

    //TODO: 단기예보 파싱

}
