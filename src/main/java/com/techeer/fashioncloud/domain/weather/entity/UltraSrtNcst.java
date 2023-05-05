package com.techeer.fashioncloud.domain.weather.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.ForecastResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.parser.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 초단기실황예보
@Getter
@NoArgsConstructor
public class UltraSrtNcst extends Forecast{
    public static final Integer TOTAL_COUNT = 8;
    public static final String BASE_MINUTE = "00";
    public static final Integer API_AVALIABLE_TIME = 40; // 매시각 45분 이후 호출 가능

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

    @Override
    public UltraSrtNcstResponse parseWeatherInfo(JsonNode itemNode) {

        // 강수형태 파싱 (코드값)
        JsonNode pty = itemNode.get(0);
        Integer ptyValue = Integer.parseInt(pty.get("obsrValue").asText());

        // 습도 파싱
        JsonNode reh = itemNode.get(1);
        Integer rehValue = Integer.parseInt(reh.get("obsrValue").asText());

        // 1시간 강수량 파싱
        JsonNode rn1 = itemNode.get(2);
        Integer rn1Value = Integer.parseInt(rn1.get("obsrValue").asText());

        // 기온 파싱
        JsonNode t1h = itemNode.get(3);
        Double t1hValue = Double.parseDouble(t1h.get("obsrValue").asText());

        // 풍속 파싱
        JsonNode wsd = itemNode.get(7);
        Double wsdValue = Double.parseDouble(t1h.get("obsrValue").asText());

        // 초단기실황 Response Dto 리턴
        return UltraSrtNcstResponse.builder()
                .temperature(t1hValue)
                .hourRainfall(rn1Value)
                .humidity(rehValue)
                .rainfallType(ptyValue)
                .windSpeed(wsdValue)
                .build();
    }
}
