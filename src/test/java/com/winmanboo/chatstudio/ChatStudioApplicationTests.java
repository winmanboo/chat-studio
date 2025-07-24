package com.winmanboo.chatstudio;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ChatStudioApplicationTests {
  
  @Autowired
  private OpenAiEmbeddingModel openAiEmbeddingModel;
  
  @Autowired
  private InMemoryEmbeddingStore<TextSegment> embeddingStore;
  
  @Test
  void contextLoads() {
  }
  
  @Test
  void verifyEmbeddingIsWorking() {
    Response<Embedding> response = openAiEmbeddingModel.embed("hello world");
    System.out.println(Arrays.toString(response.content().vector()));
  }
  
  @Test
  void loopUpMemoryEmbeddingStore() {
    System.out.println(embeddingStore.serializeToJson());
  }
}
