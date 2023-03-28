package com.techeer.fashioncloud.domain.weather.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//좌표
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Coordinate {
    private Integer nx = 55;
    private Integer ny = 127;

    // TODO: nx, ny 유효성 검사
//    public boolean isValidXY() {
//
//    }
}
