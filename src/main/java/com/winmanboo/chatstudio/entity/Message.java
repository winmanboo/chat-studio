package com.winmanboo.chatstudio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Message {
  
  public static final int DEFAULT_START_ID = 1;

  @Id
  private Integer id;
  private String sessionId;
  private String message;
  private Integer messageType;
  private Integer parentId;

  @Getter
  @RequiredArgsConstructor
  public enum Type {
    
    SYSTEM(0, "系统消息"),
    
    USER(1, "用户消息"),
    
    AI(2, "AI消息"),
    
    CUSTOM(3, "自定义消息");
    
    private final int value;
    
    private final String desc;
  }
  
  public static Message userMessage(Integer id, String sessionId, String prompt, Integer parentId) {
    return new Message(id, sessionId, prompt, Message.Type.USER.getValue(), parentId);
  }
  
  public static Message assistantMessage(Integer id, String sessionId, String prompt, Integer parentId) {
    return new Message(id, sessionId, prompt, Message.Type.AI.getValue(), parentId);
  }
}
