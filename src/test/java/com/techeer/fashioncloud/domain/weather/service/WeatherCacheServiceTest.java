package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import config.TestRedisConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@RequiredArgsConstructor
@SpringBootTest(classes = TestRedisConfig.class)
class WeatherCacheServiceTest {

    @Autowired
    private RedisTemplate<String, WeatherInfoResponse> redisTemplate;


//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    @DisplayName("날씨 정보를 캐시에 저장한다.")
    void cacheWriteTest() {
        ValueOperations<String, WeatherInfoResponse> ops = redisTemplate.opsForValue();

        WeatherInfoResponse weatherInfoResponse = WeatherInfoResponse.builder()
                .sky(0)
                .hourRainfall(0.0)
                .humidity(80)
                .windChill(20.0)
                .windSpeed(4.0)
                .temperature(20.0)
                .rainfallType(0)
                .build();
        ops.set("test", weatherInfoResponse);

        WeatherInfoResponse retrievedValue = ops.get("test");
        Assertions.assertEquals(weatherInfoResponse.getSky(), retrievedValue.getSky());
        Assertions.assertEquals(weatherInfoResponse.getHourRainfall(), retrievedValue.getHourRainfall());
        Assertions.assertEquals(weatherInfoResponse.getHumidity(), retrievedValue.getHumidity());
        Assertions.assertEquals(weatherInfoResponse.getWindChill(), retrievedValue.getWindChill());
        Assertions.assertEquals(weatherInfoResponse.getWindSpeed(), retrievedValue.getWindSpeed());
        Assertions.assertEquals(weatherInfoResponse.getTemperature(), retrievedValue.getTemperature());
        Assertions.assertEquals(weatherInfoResponse.getRainfallType(), retrievedValue.getRainfallType());
    }

    @Test
    @DisplayName("key를 가지고 날씨 정보를 업데이트한다.")
    void redisUpdateTest() {

        ValueOperations<String, WeatherInfoResponse> ops = redisTemplate.opsForValue();

        WeatherInfoResponse weatherInfoResponse = WeatherInfoResponse.builder()
                .sky(0)
                .hourRainfall(0.0)
                .humidity(70)
                .windChill(10.0)
                .windSpeed(4.0)
                .temperature(210.0)
                .rainfallType(0)
                .build();
        ops.set("test", weatherInfoResponse);

        WeatherInfoResponse retrievedValue = ops.get("test");

        Assertions.assertEquals(weatherInfoResponse.getSky(), retrievedValue.getSky());
        Assertions.assertEquals(weatherInfoResponse.getHourRainfall(), retrievedValue.getHourRainfall());
        Assertions.assertEquals(weatherInfoResponse.getHumidity(), retrievedValue.getHumidity());
        Assertions.assertEquals(weatherInfoResponse.getWindChill(), retrievedValue.getWindChill());
        Assertions.assertEquals(weatherInfoResponse.getWindSpeed(), retrievedValue.getWindSpeed());
        Assertions.assertEquals(weatherInfoResponse.getTemperature(), retrievedValue.getTemperature());
        Assertions.assertEquals(weatherInfoResponse.getRainfallType(), retrievedValue.getRainfallType());


    }
}
