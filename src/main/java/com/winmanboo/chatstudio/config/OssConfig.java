package com.winmanboo.chatstudio.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.internal.crt.DefaultS3CrtAsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfig {
  
  @Bean
  public S3AsyncClient s3AsyncClient(OssProperties ossProperties) {
    return new DefaultS3CrtAsyncClient.DefaultS3CrtClientBuilder()
        .endpointOverride(URI.create(ossProperties.getEndpoint()))
        .credentialsProvider(StaticCredentialsProvider.create(
            AwsBasicCredentials.create(ossProperties.getAccessKey(), ossProperties.getSecretKey())))
        .forcePathStyle(true)
        .region(Region.US_EAST_1/* default region */)
        .build();
  }
  
  @Bean
  public S3TransferManager transferManager(S3AsyncClient s3AsyncClient) {
    return S3TransferManager.builder()
        // .executor()
        .s3Client(s3AsyncClient)
        .build();
  }
}
