package com.winmanboo.chatstudio.controller;

import com.winmanboo.chatstudio.base.ReturnResult;
import com.winmanboo.chatstudio.domain.RegisterDTO;
import com.winmanboo.chatstudio.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public void login() {

  }

  @PostMapping("/register")
  public ReturnResult<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
    authService.register(registerDTO);
    return ReturnResult.success();
  }

  @GetMapping("/sendCode")
  public ReturnResult<Void> sendCode(@Valid @Email(message = "邮箱格式错误") String email) {
    authService.generateMailCaptcha(email);
    return ReturnResult.success();
  }
}
