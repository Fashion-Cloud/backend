package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.dto.WeatherInfoResponse;
import com.techeer.fashioncloud.domain.weather.position.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class WeatherCacheService {

    private final WeatherService weatherService;
    private final RedisTemplate<String, WeatherInfoResponse> redisTemplate;

//    @Scheduled(cron = "0 0/10 0-23 * * *\n") //매일 0시, 0시 10분, 0시 20분, ..., 23시 50분에 해당 메서드가 실행
////    @Scheduled(fixedRate = 10000) // 10초마다 실행(테스트용)
//    public void weatherCacheUpdate() {
//        Set<String> keys = getAllKeys("weatherCache"); //여기 왜 오래 걸림...?;;
//
//        //TODO: 현재는 모든 key를 받아 전체 업데이트 하는 방식 - 이후 수정 필요함
//        keys.stream().forEach(key -> {
//            WeatherInfoResponse newWeatherData = null;
//            try {
//                Coordinate coordinate = new Coordinate(55,123);
//                newWeatherData = weatherService.getNowWeather(coordinate);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            } catch (org.json.simple.parser.ParseException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("변경된 날씨데이터: "+newWeatherData);
//            redisTemplate.opsForValue().set(key, newWeatherData); //캐시 업데이트
//            //업데이트 시간도 변경해주는 작업 해야함
//        });
//
//    }

    //캐시 인스턴스에 현재 저장된 모든 key값 가져오기
    public Set<String> getAllKeys(String cacheName) {
        String pattern = cacheName + ":*";
        return redisTemplate.keys(pattern);
    }


    //문자열 형태의 key에서 x, y값 분리
    public Coordinate parseXY(String keyWithCacheName) {

        String[] key = keyWithCacheName.split("::"); //keyName분리

        if (key.length == 2) {
            String[] values = key[1].split(","); //key에서 x, y분리

            if (values.length == 2) {
                String x = values[0];
                String y = values[1];

                return new Coordinate(Integer.parseInt(x), Integer.parseInt(y));
            }
        }
        return new Coordinate(55,123);//임시데이터
    }
}