package com.techeer.fashioncloud.domain.weather.entity;

import com.techeer.fashioncloud.domain.weather.dto.VilageFcstResponse;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.parser.ParseException;

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

    //TODO: 단기예보 파싱
    @Override
    public VilageFcstResponse parseWeatherInfo(String apiResponse) throws ParseException {
        return null;
    }
}
