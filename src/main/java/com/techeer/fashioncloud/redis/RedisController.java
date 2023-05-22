package com.techeer.fashioncloud.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/redis")
    public String testRedisConnection() {
        System.out.println("test");
        redisService.checkRedisConnection();
        return "Redis 연결 확인 완료";
    }
}
