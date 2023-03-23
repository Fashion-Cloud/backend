package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.constant.WeatherConstant;
import com.techeer.fashioncloud.global.config.WeatherConfig;
import lombok.RequiredArgsConstructor;
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

    //기상청 초단기예보 api 호출
    public String getUltraSrtFcst (Integer nx, Integer ny) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(WeatherConstant.BASE_URL+WeatherConstant.ULTRA_SRT_FCST);

        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        String response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", weatherConfig.getDecodingKey())
                        .queryParam("numOfRows",19)
                        .queryParam("pageNo",1)
                        .queryParam("dataType",WeatherConstant.RESPONSE_TYPE)
                        .queryParam("base_date", setBaseDate())
                        .queryParam("base_time",setBaseTime())
                        .queryParam("nx",55)
                        .queryParam("ny",123)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //parseSkyData(초단기예보 api response);
        return response;
    }


    }

    public boolean isValidParameter() {
        //TODO: 파라미터 유효성 검사
        /*
         1. 유효한 인증키인지 (만료되지 않았는지) 확인
         2. numOfRows 0~60 사이 값인지 확인
         3. base_date가 YYYYMMDD형태이며, 유효한 연도인지 확인. (현재르 넘어서지 않는지)
         4. base_time이 HHMM (24시간단위)인지 확인, 현재를 넘어서지 않는지 확인
         5. 격자점이 우리나라 범위를 벗어나는지 확인
        */
        return true;
    }
//
//    // 기상청 초단기예보 api response에서 sky 정보만 파싱
//    public parseSkyData () {
//
//        return SKY정보;
//    }


    public Integer setBaseDate () {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.parseInt(now.format(formatter));
    }

    public String setBaseTime () {
        LocalDateTime unformattedNow = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("HH");

        String nowHour = unformattedNow.format(hourFormatter);
        String nowMinute = unformattedNow.format(minuteFormatter);

        if (Integer.parseInt(nowMinute) >= 45) {
            return Integer.toString(Integer.parseInt(nowHour) - 1) + "30";
        } else {
            return nowHour + "30";
        }
    }
}
