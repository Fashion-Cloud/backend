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
public enum SkyStatus {
    // 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)

    NONE(-1, Group.CLEAR),
    SUNNY(1, Group.CLEAR),
    CLOUDY(3, Group.CLOUDY),
    OVERCAST(4, Group.CLOUDY);

    private final Integer code;
    private final Group group;

    private static final Map<Integer, SkyStatus> skyStatusCodes = Stream.of(values()).collect(Collectors.toMap(SkyStatus::getCode, e -> e));

    public static SkyStatus findOf(Integer code) {
        return Optional.ofNullable(skyStatusCodes.get(code)).orElse(NONE);
    }

    public static List<Integer> getGroupCodes(Integer code) {
        return skyStatusCodes.values().stream()
                .filter(s -> s.getGroup().equals(findOf(code).getGroup()))
                .map(SkyStatus::getCode)
                .collect(Collectors.toList());
    }

    public static List<SkyStatus> getGroup(SkyStatus skyStatus) {
        return skyStatusCodes.values().stream()
                .filter(s -> s.getGroup().equals(skyStatus.getGroup()))
                .collect(Collectors.toList());
    }

    public enum Group {
        CLEAR, CLOUDY
    }
}
