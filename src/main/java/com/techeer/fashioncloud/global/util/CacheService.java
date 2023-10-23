package com.techeer.fashioncloud.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, String> redisTemplate;

    public String getRawValueByKey(String key) {
        if (!hasKey(key)) {
            log.trace("cache miss: {}", key);
            return null;
        }
        log.trace("cache hit: {}", key);
        return redisTemplate.opsForValue().get(key);
    }

    public void setValueByKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void increaseByKey(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
