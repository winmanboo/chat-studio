package com.winmanboo.chatstudio.service.impl;

import com.winmanboo.chatstudio.service.OssService;
import com.winmanboo.chatstudio.utils.OssUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OssServiceImpl implements OssService {
  
  private final S3TransferManager transferManager;
  
  private final OssUtil ossUtil;
  
  
  @Override
  public CompletableFuture<CompletedFileUpload> uploadFile(String bucketName, File file) {
    UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
        .putObjectRequest(b -> b.bucket(bucketName).key(ossUtil.buildDefaultObjectKey(file)))
        .source(file)
        .build();
    FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);
    return fileUpload.completionFuture();
  }
  
  @Override
  public CompletableFuture<CompletedUpload> uploadFile(String bucketName, String objectKey, byte[] fileBytes) {
    UploadRequest uploadRequest = UploadRequest.builder()
        .putObjectRequest(b -> b.bucket(bucketName).key(objectKey))
        .requestBody(AsyncRequestBody.fromBytes(fileBytes))
        .build();
    return transferManager.upload(uploadRequest).completionFuture();
  }
  
  @Override
  public void createBucket() {
  
  }
}
