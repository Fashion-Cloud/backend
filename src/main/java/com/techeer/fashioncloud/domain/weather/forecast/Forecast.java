package com.techeer.fashioncloud.domain.weather.forecast;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.global.error.exception.ExternalApiException;
import org.apache.http.HttpStatus;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Forecast {

    //api사용시간에 맞게 현재 시간을 조정하고 포매팅
    public abstract String setBaseTime();

    //현재 날짜를 포매팅하여 설정
    public String setBaseDate () {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return now.format(formatter);
    }

    //하루 전 날짜 지정
    public String getPreviousDate(String baseDate) {
        LocalDate localDateBaseDate = LocalDate.parse(baseDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate previousDate = localDateBaseDate.minusDays(1);
        return previousDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }


    // 기상청 api 정상응답 필터링 후 body 추출
    public static JsonNode filterErrorResponse(JsonNode jsonNode) throws ParseException {

        JsonNode responseNode = jsonNode.get("response");
        JsonNode headerNode = responseNode.get("header");
        String resultCode = headerNode.get("resultCode").asText();

        // 기상청 response의 resultCode가 00(정상)일때만 body 리턴
        if(resultCode.equals("00")) {
            return responseNode.get("body").get("items").get("item");
        }
        else {
            throw new ExternalApiException(HttpStatus.SC_INTERNAL_SERVER_ERROR, Integer.parseInt(resultCode), headerNode.get("resultMsg").asText()); //TODO: statuscode 삽입부분 변경
        }
    }
}