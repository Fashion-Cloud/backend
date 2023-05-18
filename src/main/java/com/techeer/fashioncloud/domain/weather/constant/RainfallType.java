package com.techeer.fashioncloud.domain.weather.constant;

import java.util.Arrays;
import java.util.List;

public enum RainfallType {
    CLEAR(0),
    RAIN(1),
    RAIN_SNOW(2),
    SNOW(3),
    RAINDROP(5),
    RAINDROP_SNOWFALL(6),
    SNOWFALL(7);

    private final int code;

    RainfallType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static List<RainfallType> getClearCodeList() {
        return Arrays.asList(CLEAR);
    }

    public static List<RainfallType> getRainyCodeList() {
        return Arrays.asList(RAIN, RAIN_SNOW, RAINDROP, RAINDROP_SNOWFALL);
    }

    public static List<RainfallType> getSnowyCodeList() {
        return Arrays.asList(SNOW, RAIN_SNOW, RAINDROP_SNOWFALL, SNOWFALL);
    }
}