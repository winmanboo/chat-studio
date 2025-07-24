package com.winmanboo.chatstudio.base;

public record ReturnResult<T>(
    int code,
    String msg,
    T data
) {
  
  public static <T> ReturnResult<T> success() {
    return success(null);
  }
  
  public static <T> ReturnResult<T> success(T data) {
    return new ReturnResult<>(0, "success", data);
  }
  
  public static <T> ReturnResult<T> failed() {
    return failed("failed");
  }
  
  public static <T> ReturnResult<T> failed(String msg) {
    return new ReturnResult<>(-1, msg, null);
  }
}
