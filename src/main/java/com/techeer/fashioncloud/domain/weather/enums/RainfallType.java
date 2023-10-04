package com.techeer.fashioncloud.domain.weather.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum RainfallType {
    // 강수형태(PTY) 코드 : (초단기) 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)

    NONE(-1, Group.CLEAR),
    CLEAR(0, Group.CLEAR),
    RAIN(1, Group.RAINY),
    RAIN_SNOW(2, Group.SNOWY),
    SNOW(3, Group.SNOWY),
    RAINDROP(5, Group.RAINY),
    RAINDROP_FLURRY(6, Group.RAINY),
    FLURRY(7, Group.SNOWY);

    private final Integer code;
    private final Group group;

    private static final Map<Integer, RainfallType> rainfallCodes = Stream.of(values()).collect(Collectors.toMap(RainfallType::getCode, e -> e));

    public static RainfallType findOf(Integer code) {
        return Optional.ofNullable(rainfallCodes.get(code)).orElse(NONE);
    }

    public static List<Integer> getGroupCodes(Integer code) {
        return rainfallCodes.values().stream()
                .filter(skyStatus -> skyStatus.getGroup().equals(findOf(code).getGroup()))
                .map(RainfallType::getCode)
                .collect(Collectors.toList());
    }

    public enum Group {
        CLEAR, RAINY, SNOWY
    }
}