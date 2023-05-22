package com.techeer.fashioncloud.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void checkRedisConnection() {
        redisTemplate.opsForValue().set("test-key", "test-value");

        String value = (String) redisTemplate.opsForValue().get("test-key");
        System.out.println("Redis 연결 확인: " + value);
    }
}
