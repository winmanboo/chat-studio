package com.winmanboo.chatstudio.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author winmanboo
 * @date 2025/5/10 10:07
 */
@Data
@ConfigurationProperties(prefix = "langchain4j")
public class ScoringProperties {

  private String apiKey;

  private String modelName;
}
