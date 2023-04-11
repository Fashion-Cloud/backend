package com.techeer.fashioncloud.domain.weather.constant;

import java.util.Arrays;
import java.util.List;

//PTY: 초단기실황 강수형태
public class RainfallType {

    public static final Integer CLEAR = 0;
    public static final Integer RAIN = 1;
    public static final Integer RAIN_SNOW = 2;
    public static final Integer SNOW = 3;
    public static final Integer RAINDROP = 5;
    public static final Integer RAINDROP_SNOWFALL = 6;
    public static final Integer SNOWFALL = 7;

    public static List<Integer> clearCodeList = Arrays.asList(CLEAR);
    public static List<Integer> RainyCodeList = Arrays.asList(RAIN, RAIN_SNOW, RAINDROP, RAINDROP_SNOWFALL);
    public static List<Integer> SnowyCodeList = Arrays.asList(SNOW, RAIN_SNOW, RAINDROP_SNOWFALL, SNOWFALL);

}
