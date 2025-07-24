package com.winmanboo.chatstudio.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatDTO {

  private String sessionId;

  private String prompt;

  private Boolean searchEnabled;

  private Boolean thinkingEnabled;

  private Boolean ragEnabled;
}
