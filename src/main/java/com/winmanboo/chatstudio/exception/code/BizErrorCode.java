package com.winmanboo.chatstudio.exception.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BizErrorCode implements ErrorCode {

  FILE_NOT_EXIST("FILE_NOT_EXIST", "文件不存在"),

  FILE_UPLOAD_FAILED("FILE_UPLOAD_FAILED", "文件上传失败"),

  SEND_CODE_LIMIT("SEND_CODE_LIMIT", "不允许重复发送验证码"),

  NOTICE_SAVE_FAILED("NOTICE_SAVE_FAILED", "通知存储失败");

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
