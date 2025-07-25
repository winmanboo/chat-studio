package com.winmanboo.chatstudio.service.impl;

import cn.hutool.core.io.FileUtil;
import com.winmanboo.chatstudio.ai.tool.DocumentLoaderTool;
import com.winmanboo.chatstudio.config.OssProperties;
import com.winmanboo.chatstudio.context.UserInfoContext;
import com.winmanboo.chatstudio.entity.KbStore;
import com.winmanboo.chatstudio.enums.UploadFileStatus;
import com.winmanboo.chatstudio.exception.BizException;
import com.winmanboo.chatstudio.service.DocumentService;
import com.winmanboo.chatstudio.service.KbService;
import com.winmanboo.chatstudio.service.KbStoreService;
import com.winmanboo.chatstudio.service.OssService;
import com.winmanboo.chatstudio.utils.OssUtil;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.model.output.TokenUsage;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.transfer.s3.model.CompletedUpload;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static com.winmanboo.chatstudio.exception.code.BizErrorCode.*;
import static com.winmanboo.chatstudio.exception.code.KnowledgeBaseErrorCode.KB_NOT_EXIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
  
  private final OssService ossService;
  
  private final KbStoreService kbStoreService;
  
  private final KbService kbService;
  
  private final OssUtil ossUtil;
  
  private final OssProperties ossProperties;
  
  private final DocumentLoaderTool documentLoaderTool;
  
  private final EmbeddingStoreIngestor embeddingStoreIngestor;
  
  @Override
  public void uploadDoc(Long kbId, MultipartFile file) throws IOException {
    log.info("upload doc start");
    if (file == null) {
      throw new BizException(FILE_NOT_EXIST);
    }
    String fileName = file.getOriginalFilename();
    String userId = UserInfoContext.get().getUserId();
    if (!kbService.isKbExists(userId, kbId)) {
      throw new BizException(KB_NOT_EXIST);
    }
    
    String prefix = FileUtil.getPrefix(fileName);
    String suffix = FileUtil.getSuffix(fileName);
    log.info("doc file name: {}.{}", prefix, suffix);
    
    String key = ossUtil.buildDocObjectKey(prefix, suffix);
    log.info("doc upload object key: {}.", key);
    KbStore kbStore = kbStoreService.saveDoc(prefix, suffix, key, kbId);
    
    // 1. upload file to oss
    OssProperties.Doc doc = ossProperties.getDoc();
    CompletableFuture<CompletedUpload> uploadFuture = ossService.uploadFile(doc.getBucketName(), key, file.getBytes());
    CompletableFuture<TokenUsage> analyzeFuture = uploadFuture.thenApplyAsync(completedUpload -> {
      if (!completedUpload.response().sdkHttpResponse().isSuccessful()) {
        kbStoreService.updateStatus(kbStore.getId(), UploadFileStatus.UPLOAD_FAILED);
        throw new BizException(FILE_UPLOAD_FAILED);
      }
      // 2. load document
      Document document = documentLoaderTool.loadDocumentForUserId(userId, doc.getBucketName(), key);
      // 3. split document
      // 4. embedding segment
      // 5. store embedding
      return embeddingStoreIngestor.ingest(document).tokenUsage();
    }, Executors.newSingleThreadExecutor()/* fixme the executor is wrong */);
    analyzeFuture.whenComplete((tokenUsage, throwable) -> {
      UploadFileStatus uploadFileStatus = throwable == null ? UploadFileStatus.ANALYZED : UploadFileStatus.ANALYZE_FAILED;
      Optional.ofNullable(throwable).ifPresentOrElse(t -> log.warn("upload doc fail, reason: {}.", t.getMessage()),
          () -> log.info("process doc success, token usage: {}.", tokenUsage));
      kbStoreService.updateStatus(kbStore.getId(), uploadFileStatus);
    });
  }
}
