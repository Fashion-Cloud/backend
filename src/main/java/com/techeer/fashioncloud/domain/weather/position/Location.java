package com.techeer.fashioncloud.domain.weather.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


//위경도
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Location {
    private Double latitude;
    private Double longitude;


    public static Coordinate getCoordinate(Double v1, Double v2) {

        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)


        double DEGRAD = Math.PI / 180.0;
        // double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("lat", v1);
//        map.put("lng", v1);
        double ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = v2 * DEGRAD - olon;
        if (theta > Math.PI)
            theta -= 2.0 * Math.PI;
        if (theta < -Math.PI)
            theta += 2.0 * Math.PI;
        theta *= sn;

        map.put("x", (int) Math.floor(ra * Math.sin(theta) + XO + 0.5));
        map.put("y", (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5));

        Coordinate coordinate = new Coordinate();

        coordinate.setNx((Integer) map.get("x"));
        coordinate.setNy((Integer) map.get("y"));

        log.info(String.valueOf(coordinate.getNx()));
        log.info(String.valueOf(coordinate.getNy()));
        log.info(String.valueOf(coordinate));

        return coordinate;
    }

}




