package com.techeer.fashioncloud.global.util;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

/*체감온도 계산 메소드
    체감온도(℃) = 13.12 + 0.6215 × 기온(℃) - 11.37 × (풍속(m/s) × 3.6)^0.16 + 0.3965 × 기온(℃) × (풍속(m/s) × 3.6)^0.16
    */
@Component
public class WindChillCalculator {
    public static Double getWindChill(final Double temperature, final Double windSpeed) {
        Double windChill =  13.12 + 0.6215 * temperature - 11.37 * Math.pow(windSpeed * 3.6, 0.16)
                + 0.3965 * temperature * Math.pow(windSpeed * 3.6, 0.16);

        //소수점 한자리까지 반올림
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(windChill));
    }
}