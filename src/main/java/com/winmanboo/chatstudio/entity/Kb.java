package com.winmanboo.chatstudio.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("kb")
public class Kb implements Serializable {
  
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  
  private Long userId;
  
  private String name;
  
  private LocalDateTime createdTime;
  
  private LocalDateTime updatedTime;
}
