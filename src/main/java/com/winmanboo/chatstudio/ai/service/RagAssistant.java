package com.winmanboo.chatstudio.ai.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.memory.ChatMemoryAccess;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

/**
 * @author winmanboo
 * @date 2025/3/22 17:32
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
    chatModel = "openAiChatModel",
    streamingChatModel = "openAiStreamingChatModel",
    chatMemoryProvider = "chatMemoryProvider",
    contentRetriever = "embeddingStoreContentRetriever",
    retrievalAugmentor = "ragRetrievalAugmentor"
)
public interface RagAssistant extends ChatMemoryAccess {
  
  Flux<String> streamingChat(@MemoryId String memoryId, @UserMessage String userInput);
}
