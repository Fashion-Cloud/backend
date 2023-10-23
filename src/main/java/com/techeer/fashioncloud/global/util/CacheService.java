package com.techeer.fashioncloud.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService<T> {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public boolean isKeyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public <U> getValueByKey(String key, Class<?> classType) throws JsonProcessingException {
        if (!isKeyExists(key)) {
            log.debug("cache miss: {}", key);
            return null;
        }
        log.debug("cache hit: {}", key);
        String value = redisTemplate.opsForValue().get(key);
        return objectMapper.readValue(value, classType);
    }


}
