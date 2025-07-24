package com.winmanboo.chatstudio.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@AllArgsConstructor
public class Session {

  @Id
  private String sessionId;

  private String title;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Message> messages;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
