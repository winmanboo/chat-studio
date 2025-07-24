package com.winmanboo.chatstudio.service;

import software.amazon.awssdk.transfer.s3.model.CompletedFileUpload;
import software.amazon.awssdk.transfer.s3.model.CompletedUpload;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public interface OssService {
  
  CompletableFuture<CompletedFileUpload> uploadFile(String bucketName, File file);
  
  CompletableFuture<CompletedUpload> uploadFile(String bucketName, String objectKey, byte[] fileBytes);
  
  void createBucket();
}
