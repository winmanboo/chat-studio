package com.winmanboo.chatstudio;

import com.winmanboo.chatstudio.ai.tool.DocumentLoaderTool;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmbeddingTests {

  @Autowired
  private DocumentLoaderTool documentLoaderTool;

  @Autowired
  private EmbeddingStoreIngestor embeddingStoreIngestor;

  @Test
  void testDocLoader() {
    Document document = documentLoaderTool.loadDocumentForUserId("doc-default", "", "<你要加载的文档路径>");
    assert document != null;
  }

  @Test
  void testEmbeddingStore() {
    Document document = documentLoaderTool.loadDocumentForUserId("default-doc", "", "doc/doc_weather_1747042767928.txt");
    assert document != null;
    IngestionResult result = embeddingStoreIngestor.ingest(document);
    System.out.println(result.tokenUsage());
  }
}
