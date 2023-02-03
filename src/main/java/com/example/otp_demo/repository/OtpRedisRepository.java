package com.example.otp_demo.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void makeData(final String key, final String value) {
        log.info("try put data in Redis key : {}, value : {}", key, value);
        redisTemplate.opsForValue()
                .setIfAbsent(key, value);
    }

    public void makeDataForDuration(final String key, final String value, long duration) {
        redisTemplate.boundValueOps(key).set(value, Duration.ofMillis(duration));
    }

    public String getValueByKey(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean isMatchByKey(final String key) {
        log.info("try match key is : {}", key);
        return redisTemplate.hasKey(key);
    }
}
