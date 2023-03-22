package com.techeer.fashioncloud.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//위경도
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Double latitude;
    private Double longitude;

    // TODO: 위도 경도로 격자값 구하는 메소드 구현
    public static Coordinate getCoordinate(Double latitude, Double longitude) {
        Integer x = 54;
        Integer y = 123;
        return new Coordinate(x,y);
    }
}
