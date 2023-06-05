package com.techeer.fashioncloud.domain.weather.constant;

import java.util.Arrays;
import java.util.List;

public class SkyStatus {
    public static final Integer CLEAR = 1;
    public static final Integer CLOUDY = 3;
    public static final Integer OVERCAST = 4;

    public static List<Integer> clearCodeList = Arrays.asList(CLEAR);

    public static List<Integer> cloudyCodeList = Arrays.asList(CLOUDY, OVERCAST);
}