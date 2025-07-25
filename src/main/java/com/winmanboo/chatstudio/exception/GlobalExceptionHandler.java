package com.winmanboo.chatstudio.exception;

import com.google.common.collect.Maps;
import com.winmanboo.chatstudio.base.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = Maps.newHashMapWithExpectedSize(1);
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HandlerMethodValidationException.class)
  public Map<String, String> handleMethodValidationExceptions(HandlerMethodValidationException ex) {
    Map<String, String> errors = Maps.newHashMapWithExpectedSize(1);
    ex.getParameterValidationResults().forEach(validationResult -> {
      String parameterName = validationResult.getMethodParameter().getParameterName();
      String errorMessage = "";
      if (!validationResult.getResolvableErrors().isEmpty()) {
        errorMessage = validationResult.getResolvableErrors().getFirst().getDefaultMessage();
      }
      errors.put(parameterName, errorMessage);
    });
    return errors;
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(BizException.class)
  public ReturnResult<Void> handleBizException(BizException e) {
    return ReturnResult.failed(e.getErrorCode());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(ChatException.class)
  public ReturnResult<Void> handleChatException(ChatException e) {
    return ReturnResult.failed(e.getErrorCode());
  }
}
