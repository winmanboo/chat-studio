package com.winmanboo.chatstudio.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionVO {

  private String sessionId;

  private String sessionTitle;

  private LocalDateTime createAt;
}
