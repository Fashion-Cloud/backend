package com.techeer.fashioncloud.domain.weather.forecast;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public abstract class Forecast {

    private static final String BASE_DATE_FORMAT = "yyyyMMdd";
    protected String baseDate;
    protected String baseTime;

    Forecast() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BASE_DATE_FORMAT);
        this.baseDate = now.format(formatter);
    }

    public abstract String getKey();

    public void setPreviousDate() {
        LocalDate localDateBaseDate = LocalDate.parse(baseDate, DateTimeFormatter.ofPattern(BASE_DATE_FORMAT));
        LocalDate previousDate = localDateBaseDate.minusDays(1);
        this.baseDate =  previousDate.format(DateTimeFormatter.ofPattern(BASE_DATE_FORMAT));
    }

}