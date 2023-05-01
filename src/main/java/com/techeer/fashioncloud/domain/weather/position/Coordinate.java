package com.techeer.fashioncloud.domain.weather.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//좌표
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Coordinate {
    private Integer nx;
    private Integer ny;

    //TODO: nx, ny 유효성 검사
//    public boolean isValidXY() {
//
//    }
}
