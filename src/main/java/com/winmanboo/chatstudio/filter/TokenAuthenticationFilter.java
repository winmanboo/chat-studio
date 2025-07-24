package com.winmanboo.chatstudio.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.winmanboo.chatstudio.base.ReturnResult;
import com.winmanboo.chatstudio.config.GlobalProperties;
import com.winmanboo.chatstudio.context.UserInfoContext;
import com.winmanboo.chatstudio.domain.UserInfo;
import com.winmanboo.chatstudio.utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenAuthenticationFilter extends OncePerRequestFilter {
  
  private final GlobalProperties globalProperties;
  
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
    if (globalProperties.getDebugMode()) {
      UserInfoContext.set(UserInfo.builder()
          .userId("2")
          .userName("admin")
          .nickName("admin")
          .capacity(-1)
          .build());
      filterChain.doFilter(request, response);
      return;
    }
    String token = getToken(request);
    if (CharSequenceUtil.isNotEmpty(token)) {
      // 根据 token 拿到用户信息
      try {
        UserInfo userInfo = parseUserInfo(token);
        UserInfoContext.set(userInfo);
        filterChain.doFilter(request, response);
        UserInfoContext.clear();
      } catch (Exception e) {
        ResponseUtil.out(response, ReturnResult.failed("用户信息获取异常"));
        return;
      }
    }
    
    ResponseUtil.out(response, ReturnResult.failed("令牌缺失"));
  }
  
  private String getToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (CharSequenceUtil.isEmpty(token)) {
      return null;
    }
    String[] tokenSplit = token.split(" ");
    if (tokenSplit.length != 2) {
      log.error("token format error: {}", token);
      return null;
    }
    String tokenType = tokenSplit[0];
    return CharSequenceUtil.removePrefixIgnoreCase(token, tokenType);
  }
  
  /**
   * 解析 jwt 并封装成 UserInfo 返回
   *
   * @param token jwt
   * @return 用户信息
   */
  private UserInfo parseUserInfo(String token) {
    JWT jwt = JWTUtil.parseToken(token);
    JWTPayload payload = jwt.getPayload();
    String userId = (String) payload.getClaim("userId");
    String userName = (String) payload.getClaim("userName");
    String nickName = (String) payload.getClaim("nickName");
    Integer capacity = (Integer) payload.getClaim("capacity");
    return UserInfo.builder()
        .userId(userId)
        .userName(userName)
        .nickName(nickName)
        .capacity(capacity)
        .build();
  }
}
