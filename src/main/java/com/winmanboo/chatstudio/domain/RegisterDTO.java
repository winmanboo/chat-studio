package com.winmanboo.chatstudio.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {

  @Email(message = "邮箱格式错误")
  private String email;

  @NotBlank(message = "密码不能为空")
  private String pwd;

  @NotBlank(message = "验证码不能为空")
  private String captcha;

  private String inviteCode;
}
