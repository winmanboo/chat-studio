package com.winmanboo.chatstudio.base;

import com.winmanboo.chatstudio.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.winmanboo.chatstudio.exception.ResponseCode.SUCCESS;

@Getter
@Setter
@AllArgsConstructor
public class ReturnResult<T> {

  private String code;

  private String msg;

  private Boolean success;

  private T data;

  public static <T> ReturnResult<T> success() {
    return success(null);
  }
  
  public static <T> ReturnResult<T> success(T data) {
    return new ReturnResult<>(SUCCESS.name(), SUCCESS.name(), true, data);
  }

  public static <T> ReturnResult<T> failed(ErrorCode errorCode) {
    return new ReturnResult<>(errorCode.getCode(), errorCode.getMessage(), false, null);
  }
}
