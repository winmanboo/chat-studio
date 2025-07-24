package com.winmanboo.chatstudio.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("kb_store")
public class KbStore implements Serializable {
  
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  
  private String fileName;
  
  private String extName;
  
  private String uploadPath;
  
  private LocalDateTime uploadDate;
  
  private Integer uploadStatus;
  
  private Long kbId;
}
