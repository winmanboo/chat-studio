package com.winmanboo.chatstudio.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author boo
 * @date 2025/7/25 15:44
 */
@Data
@TableName("user")
public class User implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.INPUT)
  private String userId;

  private String email;

  private String nickName;

  private String pwdHash;

  private String state;

  private String inviteCode;

  private String inviterId;

  private Integer capacity;

  private String profileAvatarUrl;

  @TableLogic
  private Integer deleted;

  private LocalDateTime createdTime;

  private LocalDateTime updatedTime;
}
