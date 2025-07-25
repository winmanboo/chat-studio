package com.winmanboo.chatstudio;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class MailTests {

  @Autowired
  private JavaMailSender mailSender;

  @Test
  void testSendMail() {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom("xxx@163.com");
    mailMessage.setTo("xxx@qq.com");
    mailMessage.setSubject("您的验证码");
    String code = RandomStringUtils.secure().nextNumeric(6);
    mailMessage.setText("您的验证码是：" + code + "，5 分钟内有效。");  // 邮件内容
    mailSender.send(mailMessage);
  }
}
