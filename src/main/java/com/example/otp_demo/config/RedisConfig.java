package com.example.otp_demo.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import redis.embedded.RedisServer;

@Configuration
@Slf4j
public class RedisConfig {

    @Value("${spring.data.redis.port}")
    private int port;

    private RedisServer redisServer;

    @PostConstruct
    @DependsOn("redissonClient")
    public void redisServerStart() {
        redisServer = RedisServer.builder()
                .setting("maxmemory 128M")
                .build();
        redisServer.start();
        log.info("#### embedded redis start  port :{} #######",port);
    }

    @PreDestroy
    private void redisServerStop() {
        redisServer.stop();
        log.info("#### embedded redis stop  port :{} #######",port);
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379");
        return Redisson.create(config);
    }
}
