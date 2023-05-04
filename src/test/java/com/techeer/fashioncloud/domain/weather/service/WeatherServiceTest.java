package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.exception.InvalidSkyCodeException;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.global.config.WeatherConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class WeatherServiceTest {

    @Mock
    private WeatherConfig weatherConfig;

    private WeatherService weatherService;
    @BeforeEach
    public void setUp() {
        weatherService = new WeatherService(weatherConfig);
    }

//    @Test
//    @DisplayName("유효하지 않은 위도 및 경도 입력시 Exception발생")
//    public void invalidLocation() {
//        // given
//        Location location = new Location(20.0,30.0); //범위를 벗어나는 위도 및 경도
//
//        //when, then
//        Assertions.assertThrows(InvalidSkyCodeException.class, () -> weatherS.findPostsByWeather(weather));
//
//    }



}