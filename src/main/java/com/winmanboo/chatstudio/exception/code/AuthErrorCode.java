package com.winmanboo.chatstudio.exception.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
  USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录"),

  USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),

  USER_INFO_QUERY_FAILED("USER_INFO_QUERY_FAILED", "用户信息查询失败");

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
