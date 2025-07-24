package com.winmanboo.chatstudio.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KbItemVO {
  
  private Long id;
  
  private String name;
  
  private LocalDateTime uploadDate;
  
  private Integer uploadStatus;
}
