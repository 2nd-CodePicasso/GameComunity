package com.example.codePicasso.domain.exchange.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisLockService {
    private final RedissonClient redissonClient;
    private static final String LOCK_PREFIX = "exchange:lock";

    /**
     * Redis Redisson (pub/sub)분산 락 획득
     */
    public boolean acquireLock(Long exchangeId) {
        String lockKey = LOCK_PREFIX + exchangeId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            //최대 기다림 5초. 락 획득 시 10초 유지
            return lock.tryLock(5, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 락 해제
     */
    public void releaseLock(Long exchangeId) {
        String lockKey = LOCK_PREFIX + exchangeId;
        RLock lock = redissonClient.getLock(lockKey);

        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
