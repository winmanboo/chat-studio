package com.winmanboo.chatstudio.exception.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KnowledgeBaseErrorCode implements ErrorCode {

  KB_NOT_EXIST("KB_NOT_EXIST", "知识库不存在");

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
