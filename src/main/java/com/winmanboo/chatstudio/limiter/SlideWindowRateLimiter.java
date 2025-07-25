package com.winmanboo.chatstudio.limiter;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author boo
 * @date 2025/7/25 11:56
 */
@Component
@RequiredArgsConstructor
public class SlideWindowRateLimiter implements RateLimiter {

  private static final String LIMIT_KEY_PREFIX = "chat:studio:limit:";

  private final RedissonClient redissonClient;

  @Override
  public Boolean tryAcquire(String key, int limiter, Duration windowSize) {
    RRateLimiter rateLimiter = redissonClient.getRateLimiter(LIMIT_KEY_PREFIX + key);

    if (!rateLimiter.isExists()) {
      rateLimiter.trySetRate(RateType.OVERALL, limiter, windowSize);
    }
    return rateLimiter.tryAcquire();
  }
}
