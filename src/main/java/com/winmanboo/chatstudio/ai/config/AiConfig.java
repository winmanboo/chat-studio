package com.winmanboo.chatstudio.ai.config;

import com.winmanboo.chatstudio.ai.langchain.AdvancedMessageWindowChatMemory;
import com.winmanboo.chatstudio.ai.langchain.store.MongoChatMemoryStore;
import com.winmanboo.chatstudio.config.GlobalProperties;
import com.winmanboo.chatstudio.config.OssProperties;
import com.winmanboo.chatstudio.context.UserInfoContext;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.loader.amazon.s3.AmazonS3DocumentLoader;
import dev.langchain4j.data.document.loader.amazon.s3.AwsCredentials;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder;
import dev.langchain4j.web.search.searchapi.SearchApiWebSearchEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

import java.util.HashMap;
import java.util.Map;

import static com.winmanboo.chatstudio.ai.promt.InternalPrompt.DEFAULT_INJECTOR_PROMPT_TEMPLATE;

/**
 * @author winmanboo
 * @date 2025/3/22 17:11
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ScoringProperties.class})
public class AiConfig {

    @Bean
    ChatMemoryProvider chatMemoryProvider(MongoChatMemoryStore mongoChatMemoryStore) {
        return memoryId -> AdvancedMessageWindowChatMemory.builder(memoryId)
                .maxMessages(10)
                .store(mongoChatMemoryStore)
                .build();
    }

    /**
     * 文档加载器
     */
    @Bean
    AmazonS3DocumentLoader documentLoader(OssProperties ossProperties) {
        return AmazonS3DocumentLoader.builder()
                .awsCredentials(new AwsCredentials(ossProperties.getAccessKey(), ossProperties.getSecretKey()))
                .endpointUrl(ossProperties.getEndpoint())
                .forcePathStyle(true)
                .region(Region.US_EAST_1)
                .build();
    }

    /**
     * 文档解析器
     */
    @Bean
    DocumentParser documentParser() {
        return new ApacheTikaDocumentParser();
    }

    @Bean
    EmbeddingStoreIngestor embeddingStoreIngestor(OpenAiEmbeddingModel openAiEmbeddingModel,
                                                  EmbeddingStore<TextSegment> embeddingStore) {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .embeddingModel(openAiEmbeddingModel)
                .embeddingStore(embeddingStore)
                .build();
    }
  
  /*@Bean
  QueryTransformer queryTransformer(OpenAiChatModel openAiChatModel) {
    return CompressingQueryTransformer.builder()
        .promptTemplate(DEFAULT_TRANSFORM_PROMPT_TEMPLATE)
        .chatModel(openAiChatModel)
        .build();
  }*/

    @Bean
    ChromaEmbeddingStore embeddingStore(GlobalProperties globalProperties) {
        return ChromaEmbeddingStore.builder()
                .baseUrl("http://127.0.0.1:8000")
                .logRequests(globalProperties.getDebugMode())
                .logResponses(globalProperties.getDebugMode())
                .build();
    }

    @Bean
    EmbeddingStoreContentRetriever embeddingStoreContentRetriever(OpenAiEmbeddingModel openAiEmbeddingModel,
                                                                  EmbeddingStore<TextSegment> embeddingStore) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(openAiEmbeddingModel)
                .embeddingStore(embeddingStore)
                .dynamicFilter(query -> MetadataFilterBuilder.metadataKey("userId").isEqualTo(UserInfoContext.get().getUserId()))
                .maxResults(2)
                .minScore(0.5)
                .build();
    }

    @Bean
    WebSearchContentRetriever webSearchContentRetriever() {
        Map<String, Object> optionalParameters = new HashMap<>();
        optionalParameters.put("gl", "cn");
        optionalParameters.put("hl", "zh-cn");
        optionalParameters.put("google_domain", "google.com");
        SearchApiWebSearchEngine engine = SearchApiWebSearchEngine.builder()
                .apiKey("tetgTM3Buu45pbc29zW4eVpV")
                .engine("google")
                .optionalParameters(optionalParameters)
                .build();
        return WebSearchContentRetriever.builder()
                .maxResults(10)
                .webSearchEngine(engine)
                .build();
    }

    @Bean
    RetrievalAugmentor ragRetrievalAugmentor(EmbeddingStoreContentRetriever embeddingStoreContentRetriever) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(embeddingStoreContentRetriever)
                // .contentAggregator() // 内容聚合，例如 rerank，可能要自己封装
                // 内容注入，把从 ContentRetriever 中检索出来的内容和用户的问题合并到一个 prompt 中
                .contentInjector(DefaultContentInjector.builder().promptTemplate(DEFAULT_INJECTOR_PROMPT_TEMPLATE).build())
                .build();
    }

    @Bean
    RetrievalAugmentor webRetrievalAugmentor(WebSearchContentRetriever webSearchContentRetriever) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(webSearchContentRetriever)
                // .contentAggregator() // 内容聚合，例如 rerank，可能要自己封装
                // 内容注入，把从 ContentRetriever 中检索出来的内容和用户的问题合并到一个 prompt 中
                .contentInjector(DefaultContentInjector.builder().promptTemplate(DEFAULT_INJECTOR_PROMPT_TEMPLATE).build())
                .build();
    }
}
