package com.techeer.fashioncloud.domain.weather.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.ForecastResponse;
import com.techeer.fashioncloud.global.error.exception.ExternalApiException;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Forecast {

    //api사용시간에 맞게 현재 시간을 조정하고 포매팅
    public abstract String setBaseTime();
    public abstract ForecastResponse parseWeatherInfo(JsonNode jsonNode);


    // 기상청 api 정상응답 필터링
    public static JsonNode filterErrorResponse(JsonNode jsonNode) throws ParseException {

        JsonNode responseNode = jsonNode.get("response");
        JsonNode headerNode = responseNode.get("header");
        String resultCode = headerNode.get("resultCode").asText();

        // 기상청 response의 resultCode가 00(정상)일때만 body 리턴
        if(resultCode.equals("00")) {
            return responseNode.get("body").get("items").get("item");
        }
        else {
            throw new ExternalApiException(Integer.parseInt(resultCode), headerNode.get("resultMsg").asText());
        }
    }

    //현재 날짜를 포매팅하여 설정
    public String setBaseDate () {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(formatter);
    }

}
