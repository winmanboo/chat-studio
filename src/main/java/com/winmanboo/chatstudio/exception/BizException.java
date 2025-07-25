package com.winmanboo.chatstudio.exception;

import com.winmanboo.chatstudio.exception.code.ErrorCode;

public class BizException extends BaseException {

  public BizException(ErrorCode errorCode) {
    super(errorCode);
  }

  public BizException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public BizException(String message, Throwable cause, ErrorCode errorCode) {
    super(message, cause, errorCode);
  }

  public BizException(Throwable cause, ErrorCode errorCode) {
    super(cause, errorCode);
  }

  public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
    super(message, cause, enableSuppression, writableStackTrace, errorCode);
  }
}