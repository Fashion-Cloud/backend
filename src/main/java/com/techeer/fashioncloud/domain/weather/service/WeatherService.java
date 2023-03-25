package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.constant.WeatherConstant;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.entity.UltraSrtFcst;
import com.techeer.fashioncloud.domain.weather.entity.UltraSrtNcst;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import com.techeer.fashioncloud.global.config.WeatherConfig;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherConfig weatherConfig;

    public WeatherInfoResponse getNowWeather (Coordinate coordinate) throws ParseException, org.json.simple.parser.ParseException {

        // TODO: coordinate.isValidXY(nx, ny) - 유효한 격자점인지 확인
        Integer nx = coordinate.getNx();
        Integer ny = coordinate.getNy();

        UltraSrtFcstResponse ultraSrtFcstResponse = getUltraSrtFcst(nx, ny);
        UltraSrtNcstResponse ultraSrtNcstResponse = getUltraSrtNcst(nx, ny);

        WeatherInfoResponse weatherInfo = new WeatherInfoResponse();

        return weatherInfo.getTotalWeather(ultraSrtFcstResponse, ultraSrtNcstResponse);
    }

    // 격자 좌표로 기상청 초단기예보 api 호출
    public UltraSrtFcstResponse getUltraSrtFcst(Integer nx, Integer ny) throws ParseException, org.json.simple.parser.ParseException {

        UltraSrtFcst ultraSrtFcst = new UltraSrtFcst();
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(WeatherConstant.BASE_URL+WeatherConstant.ULTRA_SRT_FCST);

        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        String response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", weatherConfig.getDecodingKey())
                        .queryParam("numOfRows",3 * UltraSrtFcst.TIME_INTERVAL + 1)
                        .queryParam("pageNo",1)
                        .queryParam("dataType","JSON")
                        .queryParam("base_date", ultraSrtFcst.setBaseDate())
                        .queryParam("base_time", ultraSrtFcst.setBaseTime())
                        .queryParam("nx",nx)
                        .queryParam("ny",ny)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ultraSrtFcst.parseWeatherInfo(response);
    }


    // 초단기실황예보로 나머지 날씨 정보 조회
    public UltraSrtNcstResponse getUltraSrtNcst (Integer nx, Integer ny) throws org.json.simple.parser.ParseException {

        UltraSrtNcst ultraSrtNcst = new UltraSrtNcst();
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(WeatherConstant.BASE_URL+WeatherConstant.ULTRA_SRT_NCST);

        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        String response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", weatherConfig.getDecodingKey())
                        .queryParam("numOfRows",UltraSrtNcst.TOTAL_COUNT)
                        .queryParam("pageNo",1)
                        .queryParam("dataType","JSON")
                        .queryParam("base_date", ultraSrtNcst.setBaseDate())
                        .queryParam("base_time", ultraSrtNcst.setBaseTime())
                        .queryParam("nx",nx)
                        .queryParam("ny",ny)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ultraSrtNcst.parseWeatherInfo(response);
    }

}
