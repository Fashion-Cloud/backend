package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.constant.UltraSrtFcstConstant;
import com.techeer.fashioncloud.domain.weather.constant.WeatherConstant;
import com.techeer.fashioncloud.domain.weather.dto.WeatherResponse;
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

    public WeatherResponse getNowWeather (Integer nx, Integer ny){

        // isValidXY - 유효한 격자점인지 확인

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
    public Integer getSkyCondition (Integer nx, Integer ny) {

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
                        .queryParam("base_time",setBaseTime())
                        .queryParam("nx",nx)
                        .queryParam("ny",ny)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // TODO: 하늘상태 정보만 파싱
        Integer skyCondition = 1;

        //parseSkyData(초단기예보 api response);
        return skyCondition;
    }




    //격자좌표가 유효한지 확인
    public boolean isValidXY() {

        return true;
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
            baseHour = Integer.toString(Integer.parseInt(nowHour) - 1);
        } else {
            baseHour = nowHour;
        }
        return baseHour + UltraSrtFcstConstant.BASE_MINUTE;
    }
}
