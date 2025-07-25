package com.winmanboo.chatstudio.limiter;

import java.time.Duration;

/**
 * @author boo
 * @date 2025/7/25 11:53
 */
public interface RateLimiter {
  /**
   * 尝试通过限流
   *
   * @param key        限流key
   * @param limiter    限流数
   * @param windowSize 窗口大小
   * @return boolean
   */
  Boolean tryAcquire(String key, int limiter, Duration windowSize);
}
