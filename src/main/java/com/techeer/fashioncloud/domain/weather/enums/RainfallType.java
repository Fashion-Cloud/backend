package com.techeer.fashioncloud.domain.weather.enums;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum RainfallType {
    // 강수형태(PTY) 코드 : (초단기) 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)

    NONE(-1),
    CLEAR(0),
    RAIN(1),
    RAIN_SNOW(2),
    SNOW(3),
    RAINDROP(5),
    RAINDROP_FLURRY(6),
    FLURRY(7);

    private final Integer code;

    RainfallType(Integer code) {
        this.code = code;
    }

    private static final Map<Integer, RainfallType> rainfallCodes = Stream.of(values()).collect(Collectors.toMap(RainfallType::getCode, e -> e));

    private static final List<Integer> clearCodes = Stream.of(NONE, CLEAR).map(RainfallType::getCode).toList();
    private static final List<Integer> rainyCodes = Stream.of(RAIN, RAIN_SNOW, RAINDROP, RAINDROP_FLURRY).map(RainfallType::getCode).toList();
    private static final List<Integer> snowyCodes = Stream.of(SNOW, RAIN_SNOW, RAINDROP_FLURRY, FLURRY).map(RainfallType::getCode).toList();

    public static RainfallType findOf(Integer code) {
        return Optional.ofNullable(rainfallCodes.get(code)).orElse(NONE);
    }
}