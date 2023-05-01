package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.constant.ForecastConstant;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.entity.UltraSrtFcst;
import com.techeer.fashioncloud.domain.weather.entity.UltraSrtNcst;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import com.techeer.fashioncloud.global.config.WeatherConfig;
import com.techeer.fashioncloud.global.util.WindChillCalculator;
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

        UltraSrtFcstResponse ultraSrtFcstResponse = getUltraSrtFcst(coordinate.getNx(), coordinate.getNy());
        UltraSrtNcstResponse ultraSrtNcstResponse = getUltraSrtNcst(coordinate.getNx(), coordinate.getNy());

        WeatherInfoResponse weatherInfo = WeatherInfoResponse.builder()
                .sky(ultraSrtFcstResponse.getSkyStatus())
                .temperature(ultraSrtNcstResponse.getTemperature())
                .hourRainfall(ultraSrtNcstResponse.getHourRainfall())
                .humidity(ultraSrtNcstResponse.getHumidity())
                .rainfallType(ultraSrtNcstResponse.getRainfallType())
                .windSpeed(ultraSrtNcstResponse.getWindSpeed())
                .windChill(WindChillCalculator.getWindChill(
                        ultraSrtNcstResponse.getTemperature(),
                        ultraSrtNcstResponse.getWindSpeed()))
                .build();

        return weatherInfo;
    }

    // 격자 좌표로 기상청 초단기예보 api 호출
    public UltraSrtFcstResponse getUltraSrtFcst(Integer nx, Integer ny) throws ParseException, org.json.simple.parser.ParseException {

        UltraSrtFcst ultraSrtFcst = new UltraSrtFcst();
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(ForecastConstant.BASE_URL+ ForecastConstant.ULTRA_SRT_FCST);

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
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(ForecastConstant.BASE_URL+ ForecastConstant.ULTRA_SRT_NCST);

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
