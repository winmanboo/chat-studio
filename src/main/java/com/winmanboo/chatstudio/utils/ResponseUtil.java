package com.winmanboo.chatstudio.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Slf4j
@UtilityClass
public class ResponseUtil {
  
  /**
   * 响应数据（兼容 jakarta 包）
   *
   * @param response 响应
   * @param object   数据
   */
  @SneakyThrows
  public static void out(jakarta.servlet.http.HttpServletResponse response, Object object) {
    if (response.isCommitted()) {
      log.trace("响应已提交不能输出");
      return;
    }
    
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    objectMapper.writeValue(response.getWriter(), object);
  }
  
  /**
   * 响应数据
   *
   * @param response 响应
   * @param object   数据
   */
    /*@SneakyThrows
    public static void out(HttpServletResponse response, Object object) {
        if (response.isCommitted()) {
            log.trace("响应已提交不能输出");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String json = objectMapper.writeValueAsString(object);
        response.getWriter().println(json);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.flushBuffer();
    }*/
  
  /**
   * 使用 Mono 响应数据
   *
   * @param response 响应
   * @param object   数据
   * @return 响应结果
   */
  @SneakyThrows
  public static Mono<Void> writeWith(ServerHttpResponse response, Object object) {
    response.setStatusCode(HttpStatus.OK);
    
    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
    
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    
    byte[] bytes = objectMapper.writeValueAsBytes(object);
    
    DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
    return response.writeWith(Mono.just(dataBuffer));
  }
}
