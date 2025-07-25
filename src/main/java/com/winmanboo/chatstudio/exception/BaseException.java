package com.winmanboo.chatstudio.exception;

import com.winmanboo.chatstudio.exception.code.ErrorCode;
import lombok.Getter;

public class BaseException extends RuntimeException{

  @Getter
  private final ErrorCode errorCode;

  public BaseException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public BaseException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public BaseException(String message, Throwable cause, ErrorCode errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public BaseException(Throwable cause, ErrorCode errorCode) {
    super(cause);
    this.errorCode = errorCode;
  }

  public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.errorCode = errorCode;
  }
}
