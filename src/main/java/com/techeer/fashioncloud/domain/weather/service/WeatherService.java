package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.constant.UltraSrtFcstConstant;
import com.techeer.fashioncloud.domain.weather.constant.WeatherConstant;
import com.techeer.fashioncloud.domain.weather.dto.WeatherResponse;
import com.techeer.fashioncloud.global.config.WeatherConfig;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherConfig weatherConfig;

    public WeatherResponse getNowWeather (Integer nx, Integer ny) throws ParseException, org.json.simple.parser.ParseException {

        // TODO: isValidXY - 유효한 격자점인지 확인

        // TODO: getWeather() - 온도 습도 등 나머지 날씨 구하기

        return WeatherResponse.builder()
                .sky(getSkyCondition(nx, ny))
                .temperature(20)
                .hourRainfall(20)
                .humidity(20)
                .rainfallType(20)
                .windSpeed(20)
                .build();
    }

    // 격자 좌표로 기상청 초단기예보 api 호출
    public Integer getSkyCondition (Integer nx, Integer ny) throws ParseException, org.json.simple.parser.ParseException {

        // isValidXY(nx, ny) - 격자 좌표 유효성 검사

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(WeatherConstant.BASE_URL+WeatherConstant.ULTRA_SRT_FCST);

        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        String response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", weatherConfig.getDecodingKey())
                        .queryParam("numOfRows",3 * UltraSrtFcstConstant.TIME_INTERVAL + 1)
                        .queryParam("pageNo",1)
                        .queryParam("dataType","JSON")
                        .queryParam("base_date", setBaseDate())
                        .queryParam("base_time", setBaseTime())
                        .queryParam("nx",nx)
                        .queryParam("ny",ny)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseSkyCondition(response);
    }


    // TODO: 격자좌표가 유효한지 확인
    public boolean isValidXY() {

        return true;
    }

    // TODO: 파싱 코드 개선
    // 하늘상태 정보 파싱
    public Integer parseSkyCondition(String apiResponse) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(apiResponse);


        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        JSONObject jsonItems = (JSONObject) jsonBody.get("items");
        JSONArray jsonItem = (JSONArray) jsonItems.get("item");

        JSONObject skyData = (JSONObject) jsonItem.get(18);
        Integer skyValue = Integer.parseInt((String) skyData.get("fcstValue"));

        return skyValue;
    }


    // 현재 날짜로 설정하고 포매팅하여 base_date 지정
    public Integer setBaseDate () {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.parseInt(now.format(formatter));
    }

    // 현재 시간을 설정하고 포매팅하여 base_time 지정
    public String setBaseTime () {
        LocalDateTime unformattedNow = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");

        String nowHour = unformattedNow.format(hourFormatter);
        String nowMinute = unformattedNow.format(minuteFormatter);
        String baseHour;

        // api 호출 시간에 따라 base_time 다르게 설정
        if (Integer.parseInt(nowMinute) >= UltraSrtFcstConstant.API_AVALIABLE_TIME) {
            baseHour = nowHour;
        } else {
            baseHour = Integer.toString(Integer.parseInt(nowHour) - 1);
        }
        return baseHour + UltraSrtFcstConstant.BASE_MINUTE;
    }
}
