package com.winmanboo.chatstudio.context;

import com.winmanboo.chatstudio.domain.UserInfo;
import com.winmanboo.chatstudio.exception.ServerException;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class UserInfoContext {
  
  private static final ThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL = new InheritableThreadLocal<>();
  
  public UserInfo get() {
    UserInfo userInfo = USER_INFO_THREAD_LOCAL.get();
    if (Objects.isNull(userInfo)) {
      throw new ServerException("用户信息获取失败");
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
