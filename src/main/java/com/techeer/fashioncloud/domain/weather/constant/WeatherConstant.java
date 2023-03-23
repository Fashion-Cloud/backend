package com.techeer.fashioncloud.domain.weather.constant;

//uri생성용 상수
public class WeatherConstant {
    public static final String BASE_URL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/";

    public static final String ULTRA_SRT_NCST = "getUltraSrtNcst"; //초단기실황조회

    public static final String ULTRA_SRT_FCST = "getUltraSrtFcst"; // 초단기예보조회

    public static final String VILAGE_FCST = "getVilageFcst"; //단기예보조회

    public static final String RESPONSE_TYPE = "JSON";
}
