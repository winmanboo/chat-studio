package com.winmanboo.chatstudio.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMethodCache(basePackages = "com.winmanboo.chatstudio")
public class CacheConfig {
}
