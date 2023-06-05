package com.techeer.fashioncloud.domain.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.constant.ForecastConstant;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtFcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.UltraSrtNcstResponse;
import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.forecast.Forecast;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtFcst;
import com.techeer.fashioncloud.domain.weather.forecast.UltraSrtNcst;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import com.techeer.fashioncloud.global.config.WeatherConfig;
import com.techeer.fashioncloud.global.error.exception.ApiBadRequestException;
import com.techeer.fashioncloud.global.error.exception.ApiParseException;
import com.techeer.fashioncloud.global.error.exception.ApiServerErrorException;
import com.techeer.fashioncloud.global.util.WindChillCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherConfig weatherConfig;
    private final RedisTemplate<String, WeatherInfoResponse> redisTemplate;

    public WeatherInfoResponse getNowWeather(Coordinate coordinate) throws ParseException, org.json.simple.parser.ParseException {


        UltraSrtFcstResponse ultraSrtFcstResponse = getUltraSrtFcst(coordinate.getNx(), coordinate.getNy());
        UltraSrtNcstResponse ultraSrtNcstResponse = getUltraSrtNcst(coordinate.getNx(), coordinate.getNy());

        WeatherInfoResponse weatherInfoResponse = WeatherInfoResponse.builder()
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

        // 캐시에 데이터 저장
        redisTemplate.opsForValue().set("weatherCache::"+coordinate.getNx()+","+coordinate.getNy(), weatherInfoResponse);

        return weatherInfoResponse;
    }

    // 격자 좌표로 기상청 초단기예보 api 호출
    public UltraSrtFcstResponse getUltraSrtFcst(Integer nx, Integer ny) {

        UltraSrtFcst ultraSrtFcst = new UltraSrtFcst();
        String baseDate = ultraSrtFcst.setBaseDate();
        String baseTime = ultraSrtFcst.setBaseTime();
        if (baseTime.equals("2330")) {
            final String newBaseDate = ultraSrtFcst.getPreviousDate(baseDate); //11시 반 데이터를 사용하는 경우 baseDate 조정
        }
        String finalBaseDate = baseDate;

        log.info("초단기예보 UltraSrtFcst - BaseDate: {}, BaseTime: {}", baseDate, baseTime);

        HashMap<String, Object> params = new HashMap<>() {
            {
                put("numOfRows", 3 * UltraSrtFcst.TIME_INTERVAL + 1);
                put("pageNo", 1);
                put("base_date", finalBaseDate);
                put("base_time", baseTime);
                put("nx", nx);
                put("ny", ny);
            }
        };

        Mono<JsonNode> responseMono = getResponseMono(ForecastConstant.BASE_URL + ForecastConstant.ULTRA_SRT_FCST, params);

        return responseMono.map(jsonNode -> {
            try {
                JsonNode itemNode = Forecast.filterErrorResponse(jsonNode); //정상 응답 필터링
                return ultraSrtFcst.parseWeatherInfo(itemNode);

            } catch (org.json.simple.parser.ParseException e) {
                throw new ApiParseException();
            }
        }).block();
    }

    // 초단기실황예보로 나머지 날씨 정보 조회
    public UltraSrtNcstResponse getUltraSrtNcst(Integer nx, Integer ny) throws org.json.simple.parser.ParseException {

        UltraSrtNcst ultraSrtNcst = new UltraSrtNcst();
        String baseDate = ultraSrtNcst.setBaseDate();
        String baseTime = ultraSrtNcst.setBaseTime();
        if (baseTime.equals("2330")) {
            baseDate = ultraSrtNcst.getPreviousDate(baseDate); //11시 반 데이터를 사용하는 경우 baseDate 조정
        }

        log.info("초단기실황예보 UltraSrtNcst - BaseDate: {}, BaseTime: {}", baseDate, baseTime);

        String finalBaseDate = baseDate;
        HashMap<String, Object> params = new HashMap<>() {
            {
                put("numOfRows", UltraSrtNcst.TOTAL_COUNT);
                put("pageNo", 1);
                put("base_date", finalBaseDate);
                put("base_time", baseTime);
                put("nx", nx);
                put("ny", ny);
            }
        };

        Mono<JsonNode> responseMono = getResponseMono(ForecastConstant.BASE_URL + ForecastConstant.ULTRA_SRT_NCST, params);

        return responseMono.map(jsonNode -> {
            try {
                JsonNode itemNode = Forecast.filterErrorResponse(jsonNode); //정상 응답 필터링
                return ultraSrtNcst.parseWeatherInfo(itemNode);

            } catch (org.json.simple.parser.ParseException e) {
                throw new ApiParseException();
            }
        }).block();
    }

    public Mono<JsonNode> getResponseMono(String path, HashMap<String, Object> params) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(path);

        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        return webclient.get()
                .uri(uriBuilder -> {
                    uriBuilder.queryParam("serviceKey", weatherConfig.getDecodingKey());
                    uriBuilder.queryParam("dataType", "JSON");
                    params.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })

                .exchangeToMono(response -> {
                    Integer httpStatusCode = response.statusCode().value();
                    HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode);

                    if (httpStatus.is2xxSuccessful()) {
                        return response.bodyToMono(JsonNode.class);
                    } else if (httpStatus.is4xxClientError()) {
                        log.error("Exception occurred - status: {}, message: {}", httpStatus, httpStatus.getReasonPhrase());
                        throw new ApiBadRequestException();
                    } else {
                        log.error("Exception occurred - status: {}, message: {}", httpStatus, httpStatus.getReasonPhrase());
                        throw new ApiServerErrorException();
                    }
                });
    }
}

