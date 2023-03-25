package com.techeer.fashioncloud.domain.weather.entity;

import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
        String baseHour;

        // api 호출 시간에 따라 base_time 다르게 설정
        if (Integer.parseInt(nowMinute) >= UltraSrtNcst.API_AVALIABLE_TIME) {
            baseHour = nowHour;
            return baseHour + BASE_MINUTE;
        } else {
            baseHour = Integer.toString(Integer.parseInt(nowHour) - 1);
            return baseHour + BASE_MINUTE;
        }
    }

    // TODO: 파싱 코드 개선
    @Override
    public UltraSrtNcstResponse parseWeatherInfo(String apiResponse) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(apiResponse);


        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONObject jsonItems = (JSONObject) jsonBody.get("items");
        JSONArray jsonItem = (JSONArray) jsonItems.get("item");

        // 강수형태 파싱 (코드값)
        JSONObject pty = (JSONObject) jsonItem.get(0);
        Integer ptyValue = Integer.parseInt((String) pty.get("obsrValue"));

        // 습도 파싱
        JSONObject reh = (JSONObject) jsonItem.get(1);
        Integer rehValue = Integer.parseInt((String) reh.get("obsrValue"));

        // 1시간 강수량 파싱
        JSONObject rn1 = (JSONObject) jsonItem.get(2);
        Integer rn1Value = Integer.parseInt((String) rn1.get("obsrValue"));

        // 기온 파싱
        JSONObject t1h = (JSONObject) jsonItem.get(3);
        Double t1hValue = Double.parseDouble((String) t1h.get("obsrValue"));

        // 풍속 파싱
        JSONObject wsd = (JSONObject) jsonItem.get(7);
        Double wsdValue = Double.parseDouble((String) wsd.get("obsrValue"));

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
