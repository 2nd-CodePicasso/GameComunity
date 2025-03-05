package com.example.codePicasso.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
            .setAddress("redis://localhost:6379") // Redis 서버 주소
            .setConnectionMinimumIdleSize(5)
            .setConnectionPoolSize(10);
        return Redisson.create(config);
    }
}
