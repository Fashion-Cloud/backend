package com.techeer.fashioncloud.domain.weather.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//위경도
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Location {
    private Double latitude;
    private Double longitude;


    public static Coordinate getCoordinate(Double latitude, Double longitude) {
        Integer x = 54;
        Integer y = 123;

        /*
        TODO: 위도 경도로 격자값 구하는 메소드 구현
        여기에 격자값 변환 로직 작성!!
        */

        return new Coordinate(x,y);
    }
}
