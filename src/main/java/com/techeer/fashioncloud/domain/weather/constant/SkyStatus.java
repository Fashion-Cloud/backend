package com.techeer.fashioncloud.domain.weather.constant;

import java.util.Arrays;
import java.util.List;

public enum SkyStatus {
    CLEAR(1),
    CLOUDY(3),
    OVERCAST(4);

    private final int code;

    SkyStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static List<SkyStatus> getClearCodeList() {
        return Arrays.asList(CLEAR);
    }

    public static List<SkyStatus> getCloudyCodeList() {
        return Arrays.asList(CLOUDY, OVERCAST);
    }
}