package com.winmanboo.chatstudio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class GlobalProperties {
  
  private Boolean debugMode = true;
}
