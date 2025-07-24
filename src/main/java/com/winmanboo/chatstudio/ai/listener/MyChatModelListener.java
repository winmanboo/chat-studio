package com.winmanboo.chatstudio.ai.listener;

import com.winmanboo.chatstudio.config.GlobalProperties;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.ChatResponseMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyChatModelListener implements ChatModelListener {
  
  private final GlobalProperties globalProperties;
  
  @Override
  public void onRequest(ChatModelRequestContext requestContext) {
    ChatRequest chatRequest = requestContext.chatRequest();
    ChatMessage userMessage = CollectionUtils.lastElement(chatRequest.messages());
    if (globalProperties.getDebugMode()) {
      log.info("chat request ----> model provider: {}, model name: {}, user input: {}",
          requestContext.modelProvider(), chatRequest.modelName(), userMessage);
    }
  }
  
  @Override
  public void onResponse(ChatModelResponseContext responseContext) {
    ChatResponse chatResponse = responseContext.chatResponse();
    AiMessage aiMessage = chatResponse.aiMessage();
    ChatResponseMetadata metadata = chatResponse.metadata();
    if (globalProperties.getDebugMode()) {
      log.info("chat response ----> model provider: {}, model name: {}, ai response: {}, metadata info: {}",
          responseContext.modelProvider(), chatResponse.modelName(), aiMessage, metadata);
    }
  }
  
  @Override
  public void onError(ChatModelErrorContext errorContext) {
    log.error("chat error: ", errorContext.error());
  }
}
