package com.winmanboo.chatstudio.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class SaTokenConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SaInterceptor(handler -> {
      SaRouter.match("/**")
          .notMatch("/auth/login", "/auth/register", "/auth/sendCode")
          .check(r -> StpUtil.checkLogin());

      // 根据路由划分模块，不同模块不同鉴权
      // SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
      // SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
      // SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
      // SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
      // SaRouter.match("/notice/**", r -> StpUtil.checkPermission("notice"));
      // SaRouter.match("/comment/**", r -> StpUtil.checkPermission("comment"));
    })).addPathPatterns("/**").excludePathPatterns("/favicon.ico", "/error");
  }
}
