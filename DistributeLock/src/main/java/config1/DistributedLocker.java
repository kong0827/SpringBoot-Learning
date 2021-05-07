package config1;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @author xiangjin.kong
 * @date 2021/5/7 14:43
 */
public interface DistributedLocker {
    RLock lock(String lockKey);

    RLock lock(String lockKey, int timeout);

    RLock lock(String lockKey, TimeUnit unit, int timeout);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

}
