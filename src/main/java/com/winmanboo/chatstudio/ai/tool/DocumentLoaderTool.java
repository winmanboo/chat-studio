package com.winmanboo.chatstudio.ai.tool;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.loader.amazon.s3.AmazonS3DocumentLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentLoaderTool {
  
  private final AmazonS3DocumentLoader amazonS3DocumentLoader;
  
  private final DocumentParser documentParser;
  
  public Document loadDocumentForUserId(String userId, String bucketName, String key) {
    Document document = amazonS3DocumentLoader.loadDocument(bucketName, key, documentParser);
    document.metadata().put("userId", userId);
    return document;
  }
}
