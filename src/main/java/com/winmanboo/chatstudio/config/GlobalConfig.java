package com.winmanboo.chatstudio.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GlobalProperties.class)
public class GlobalConfig {
}
