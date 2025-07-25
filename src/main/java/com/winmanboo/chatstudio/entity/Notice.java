package com.winmanboo.chatstudio.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.winmanboo.chatstudio.enums.NoticeState;
import com.winmanboo.chatstudio.enums.NoticeType;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author boo
 * @date 2025/7/25 14:00
 */
@Data
@Builder
@TableName("notice")
public class Notice implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;

  private String noticeTitle;

  private String noticeContent;

  private NoticeType noticeType;

  private LocalDateTime sendTime;

  private String receiveAddress;

  private NoticeState state;

  @TableLogic
  private Integer deleted;

  private Integer retryTimes;

  private String extendInfo;
}
