package com.winmanboo.chatstudio.context;

import com.winmanboo.chatstudio.domain.UserInfo;
import com.winmanboo.chatstudio.exception.BizException;
import lombok.experimental.UtilityClass;

import java.util.Objects;

import static com.winmanboo.chatstudio.exception.code.AuthErrorCode.USER_INFO_QUERY_FAILED;

@UtilityClass
public class UserInfoContext {

  private static final ThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL = new InheritableThreadLocal<>();

  public UserInfo get() {
    UserInfo userInfo = USER_INFO_THREAD_LOCAL.get();
    if (Objects.isNull(userInfo)) {
      throw new BizException(USER_INFO_QUERY_FAILED);
    }
    return userInfo;
  }

  public void set(UserInfo userInfo) {
    USER_INFO_THREAD_LOCAL.set(userInfo);
  }

  public void clear() {
    UserInfo userInfo = USER_INFO_THREAD_LOCAL.get();

    if (Objects.nonNull(userInfo)) {
      USER_INFO_THREAD_LOCAL.remove();
    }
  }
}
