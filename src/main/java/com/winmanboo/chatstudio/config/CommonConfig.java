package com.winmanboo.chatstudio.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Configuration
public class CommonConfig {

  @Bean
  @Qualifier("ioIntensiveThreadPool")
  public ThreadPoolExecutor ioIntensiveThreadPool() {
    int corePoolSize = Runtime.getRuntime().availableProcessors();
    int maxPoolSize = corePoolSize * 2;
    ThreadFactory threadFactory = new CustomizableThreadFactory("ioIntensive-threadPool-");
    return new ThreadPoolExecutor(
        corePoolSize,
        maxPoolSize,
        60L,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(),
        threadFactory,
        new ThreadPoolExecutor.CallerRunsPolicy()
    );
  }
}
