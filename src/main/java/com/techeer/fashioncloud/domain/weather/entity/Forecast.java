package com.techeer.fashioncloud.domain.weather.entity;

import com.techeer.fashioncloud.domain.weather.dto.ForecastResponse;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Forecast {
    //api사용시간에 맞게 현재 시간을 조정하고 포매팅
    public abstract String setBaseTime();
    public abstract ForecastResponse parseWeatherInfo(String apiResponse) throws ParseException;

    //현재 날짜를 포매팅하여 설정
    public String setBaseDate () {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(formatter);
    }

}
