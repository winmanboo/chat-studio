package com.winmanboo.chatstudio.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
  
  private String userId;
  
  private String userName;
  
  private String nickName;
  
  private Integer capacity;
}
