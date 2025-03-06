package com.example.codePicasso.domain.exchange.redis;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockService {

    private final StatefulRedisConnection<String, String> connection;
    private static final String LOCK_PREFIX = "exchange:lock";
    private static final long LOCK_EXPIRY = 10;
    private static final long SPIN_INTERVAL = 1;

    /**
     * Redis Redisson (pub/sub)분산 락 획득
     */
    public boolean acquireLock(Long gameId) {
        String lockKey = LOCK_PREFIX + gameId;
        RedisCommands<String, String> commands = connection.sync();
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(5).toMillis();
        while (System.currentTimeMillis() < endTime) {
            String result = commands.set(lockKey, "locked", SetArgs.Builder.nx().ex(LOCK_EXPIRY));
            if ("OK".equals(result)) {
                return true;
            }
            try {
                Thread.sleep(SPIN_INTERVAL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }


    /**
     * 락 해제
     */
    public void releaseLock(Long gameId) {
        String lockKey = LOCK_PREFIX + gameId;
        RedisCommands<String, String> commands = connection.sync();
        commands.del(lockKey);
    }

}
