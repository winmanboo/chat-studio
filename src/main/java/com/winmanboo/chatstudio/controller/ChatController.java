package com.winmanboo.chatstudio.controller;

import com.winmanboo.chatstudio.ai.service.RagAssistant;
import com.winmanboo.chatstudio.ai.service.UniversalAssistant;
import com.winmanboo.chatstudio.ai.service.WebAssistant;
import com.winmanboo.chatstudio.base.ReturnResult;
import com.winmanboo.chatstudio.domain.ChatDTO;
import com.winmanboo.chatstudio.domain.SessionVO;
import com.winmanboo.chatstudio.entity.Message;
import com.winmanboo.chatstudio.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author winmanboo
 * @date 2025/3/22 17:24
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatController {
  
  private final ChatService chatService;
  
  private final RagAssistant ragAssistant;
  
  private final UniversalAssistant universalAssistant;
  
  private final WebAssistant webAssistant;
  
  @PostMapping("/session/create")
  public ReturnResult<String> create() {
    return ReturnResult.success(chatService.create());
  }
  
  @PostMapping(value = "/chat", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
  public Flux<String> chat(@RequestBody ChatDTO chatDTO) {
    if (chatDTO.getRagEnabled()) {
      return ragAssistant.streamingChat(chatDTO.getSessionId(), chatDTO.getPrompt());
    } else if (chatDTO.getSearchEnabled()) {
      return webAssistant.streamingChat(chatDTO.getSessionId(), chatDTO.getPrompt());
    }
    return universalAssistant.streamingChat(chatDTO.getSessionId(), chatDTO.getPrompt());
  }
  
  @GetMapping("/sessions")
  public ReturnResult<List<SessionVO>> sessions() {
    return ReturnResult.success(chatService.sessions());
  }
  
  @GetMapping("/messages/{sessionId}")
  public ReturnResult<List<Message>> messages(@PathVariable String sessionId) {
    return ReturnResult.success(chatService.messages(sessionId));
  }
  
  @DeleteMapping("/session/delete/{sessionId}")
  public ReturnResult<Void> deleteSession(@PathVariable("sessionId") String sessionId) {
    chatService.deleteSession(sessionId);
    return ReturnResult.success();
  }
}