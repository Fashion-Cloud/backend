package com.techeer.fashioncloud.domain.weather.util;

import java.text.DecimalFormat;

/*
체감온도 계산식
체감온도(℃) = 13.12 + 0.6215 × 기온(℃) - 11.37 × (풍속(m/s) × 3.6)^0.16 + 0.3965 × 기온(℃) × (풍속(m/s) × 3.6)^0.16
*/
public class WindChillCalculator {
    public static Double getWindChill(final Double temperature, final Double windSpeed) {
        Double windChill = 13.12 + 0.6215 * temperature - 11.37 * Math.pow(windSpeed * 3.6, 0.16)
                + 0.3965 * temperature * Math.pow(windSpeed * 3.6, 0.16);

        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(windChill));
    }
}