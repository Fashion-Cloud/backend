package com.techeer.fashioncloud.domain.weather.forecast;

import com.techeer.fashioncloud.domain.weather.constant.ForecastConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

// 초단기예보
@Getter
@NoArgsConstructor
public class UltraSrtFcst extends Forecast implements WeatherApiCallable {

    public static final String REQ_URL = ForecastConstant.BASE_URL + ForecastConstant.ULTRA_SRT_FCST;
    public static final Integer TIME_INTERVAL = 6;
    public static final Integer SKY_DATA_INDEX = 4;
    public static final Integer API_AVALIABLE_MINUTE = 45;
    public static final String BASE_MINUTE = "30";
    public static final String DATE_CHANGE_TIME = "2330";
    public static final String keyPrefix = "ultraSrtFcst:";

    private Integer nx;
    private Integer ny;

    public UltraSrtFcst (Integer nx, Integer ny) {

        this.nx = nx;
        this.ny = ny;

        LocalDateTime unformattedNow = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");

        String nowHour = unformattedNow.format(hourFormatter);
        String nowMinute = unformattedNow.format(minuteFormatter);
        int nowIntHour = Integer.parseInt(nowHour);

        if (Integer.parseInt(nowMinute) >= API_AVALIABLE_MINUTE) {
            this.baseTime = nowHour + BASE_MINUTE;
        } else {
            if(nowIntHour == 0) this.baseTime = "23" + BASE_MINUTE;
            else if(nowIntHour < 10) this.baseTime = "0" + (nowIntHour-1) + BASE_MINUTE;
            else this.baseTime =  (nowIntHour-1) + BASE_MINUTE;
        }

        if(baseTime.equals(DATE_CHANGE_TIME)) setPreviousDate();
    }

    public HashMap<String, Object> getReqQueryParams () {

        HashMap<String, Object> reqQueryParams = new HashMap<>();
        reqQueryParams.put("numOfRows", TIME_INTERVAL);
        reqQueryParams.put("pageNo", SKY_DATA_INDEX);
        reqQueryParams.put("base_date", baseDate);
        reqQueryParams.put("base_time", baseTime);
        reqQueryParams.put("nx", nx);
        reqQueryParams.put("ny", ny);

        return reqQueryParams;
    }

    public String getKey() {
        return keyPrefix + nx + "," + ny + ":" + baseDate + "-"+ baseTime;
    }
}