package com.techeer.fashioncloud.domain.weather.enums;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum SkyStatus {
    // 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)

    NONE(-1),
    SUNNY(1),
    CLOUDY(3),
    OVERCAST(4);

    private final Integer code;

    SkyStatus(Integer code) {
        this.code = code;
    }

    private static final Map<Integer, SkyStatus> skyStatusCodes = Stream.of(values()).collect(Collectors.toMap(SkyStatus::getCode, e -> e));

    public static SkyStatus findOf(Integer code) {
        return Optional.ofNullable(skyStatusCodes.get(code)).orElse(NONE);
    }

}
