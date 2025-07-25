package com.winmanboo.chatstudio.exception.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {
  SESSION_NOT_EXIST("SESSION_NOT_EXIST", "会话不存在"),

  FIRST_MESSAGE_ERROR("FIRST_MESSAGE_ERROR", "会话消息第一条消息必须是用户消息"),

  CREATE_SESSION_FAILED("CREATE_SESSION_FAILED", "创建会话失败");

  private final String code;

  private final String message;

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
