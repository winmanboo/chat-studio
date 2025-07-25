package com.winmanboo.chatstudio.exception;


import com.winmanboo.chatstudio.exception.code.ErrorCode;

public class ChatException extends BaseException {

  public ChatException(ErrorCode errorCode) {
    super(errorCode);
  }

  public ChatException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public ChatException(String message, Throwable cause, ErrorCode errorCode) {
    super(message, cause, errorCode);
  }

  public ChatException(Throwable cause, ErrorCode errorCode) {
    super(cause, errorCode);
  }

  public ChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
    super(message, cause, enableSuppression, writableStackTrace, errorCode);
  }
}
