package com.techeer.fashioncloud.domain.weather.forecast;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 초단기예보
@Getter
@NoArgsConstructor
public class UltraSrtFcst extends Forecast {

    public static final Integer TIME_INTERVAL = 6; //초단기예보조회 데이터 시간 간격

    public static final String BASE_MINUTE = "30";
    public static final Integer API_AVALIABLE_TIME = 45; // 매시각 45분 이후 호출 가능

    @Override
    // 현재 시간을 설정하고 포매팅하여 base_time 지정
    public String setBaseTime () {
        LocalDateTime unformattedNow = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");

        String nowHour = unformattedNow.format(hourFormatter);
        String nowMinute = unformattedNow.format(minuteFormatter);
        Integer baseHour;

        // api 호출 시간에 따라 base_time 다르게 설정
        if (Integer.parseInt(nowMinute) >= API_AVALIABLE_TIME) {
            return nowHour + BASE_MINUTE;
        } else {
            baseHour = Integer.parseInt(nowHour) - 1;
            if(baseHour == 0) return "23" + BASE_MINUTE;
            else if(baseHour < 10) return "0" + baseHour.toString() + BASE_MINUTE;
            else return baseHour + BASE_MINUTE;
        }
    }

    // 초단기예보 응답 파싱하여 sky상태만 반환
    @Override
    public UltraSrtFcstResponse parseWeatherInfo(JsonNode itemNode) {

        JsonNode skyData = (JsonNode) itemNode.get(18); //TODO: 하드코딩 개선

        // 초단기예보조회 Response Dto 리턴
        return UltraSrtFcstResponse.builder()
                .skyStatus(Integer.parseInt(skyData.get("fcstValue").asText()))
                .build();
    }
}
