package com.winmanboo.chatstudio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
  
  private String endpoint;
  
  private String accessKey;
  
  private String secretKey;
  
  private String defaultBucketName = "default";
  
  private String defaultPathStyleAccess = "public";
  
  private String defaultObjectKeyFormat = "default_%s_%s.%s";
  
  private Doc doc;
  
  @Data
  public static class Doc {
    
    private String bucketName;
    
    private String pathStyleAccess = "doc";
    
    private String objectKeyFormat = "doc_$s_$s.%s";
  }
}
