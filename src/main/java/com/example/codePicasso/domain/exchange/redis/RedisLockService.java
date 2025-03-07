package com.example.codePicasso.domain.exchange.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final RedissonClient redissonClient;
    private static final String LOCK_PREFIX = "exchange:lock";

    /**
     * Redis Redisson (pub/sub)분산 락 획득
     */
    public boolean acquireLock(Long gameId) {
        String lockKey = LOCK_PREFIX + gameId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            //최대 기다림 5초. 락 획득 시 10초 유지
            return lock.tryLock(10, 3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }


    /**
     * 락 해제
     */
    public void releaseLock(Long gameId) {
        String lockKey = LOCK_PREFIX + gameId;
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

}
