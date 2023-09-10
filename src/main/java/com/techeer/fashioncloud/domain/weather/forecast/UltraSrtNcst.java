package com.techeer.fashioncloud.domain.weather.forecast;

import com.techeer.fashioncloud.domain.weather.constant.ForecastConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

// 초단기실황예보
@Getter
@NoArgsConstructor
public class UltraSrtNcst extends Forecast implements WeatherApiCallable {

    public static final String REQ_URL = ForecastConstant.BASE_URL + ForecastConstant.ULTRA_SRT_NCST;
    public static final Integer TOTAL_COUNT = 8;
    public static final String BASE_MINUTE = "00";
    public static final Integer API_AVALIABLE_MINUTE = 40;
    public static final String DATE_CHANGE_TIME = "2300";
    public static final String keyPrefix = "ultraSrtNcst:";

    private Integer nx;
    private Integer ny;

    public UltraSrtNcst (Integer nx, Integer ny) {

        this.nx = nx;
        this.ny = ny;

        LocalDateTime unformattedNow = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");

        String nowHour = unformattedNow.format(hourFormatter);
        String nowMinute = unformattedNow.format(minuteFormatter);
        int nowIntHour = Integer.parseInt(nowHour);

        if (Integer.parseInt(nowMinute) >= API_AVALIABLE_MINUTE) {
            this.baseTime =  nowHour + BASE_MINUTE;
        } else {
            if(nowIntHour == 0) this.baseTime =  "23" + BASE_MINUTE;
            else if(nowIntHour < 10) this.baseTime = "0" + (nowIntHour-1) + BASE_MINUTE;
            else this.baseTime = (nowIntHour-1) + BASE_MINUTE;
        }

        if(baseTime.equals(DATE_CHANGE_TIME)) setPreviousDate();
    }

    @Override
    public String getKey() {
        return keyPrefix + nx + "," + ny + ":" + baseTime;
    }

    @Override
    public HashMap<String, Object> getReqQueryParams () {

        HashMap<String, Object> reqQueryParams = new HashMap<>();

        reqQueryParams.put("numOfRows", TOTAL_COUNT);
        reqQueryParams.put("pageNo", 1);
        reqQueryParams.put("base_date", baseDate);
        reqQueryParams.put("base_time", baseTime);
        reqQueryParams.put("nx", nx);
        reqQueryParams.put("ny", ny);

        return reqQueryParams;
    }
}
