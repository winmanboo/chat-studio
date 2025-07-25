package com.winmanboo.chatstudio.service.impl;

import com.winmanboo.chatstudio.entity.Notice;
import com.winmanboo.chatstudio.enums.NoticeState;
import com.winmanboo.chatstudio.service.MailService;
import com.winmanboo.chatstudio.service.NoticeService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final JavaMailSender mailSender;

  private final TemplateEngine templateEngine;

  private final NoticeService noticeService;

  @Value("${spring.mail.username}")
  private String from;

  @Override
  @Async("ioIntensiveThreadPool")
  public void sendCode(String email, String captcha, Notice notice) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
      helper.setFrom(from);
      helper.setTo(email);
      helper.setSubject("Chat Studio Register");

      Context context = new Context();
      context.setVariable("username", email.substring(0, email.indexOf("@")));
      context.setVariable("appName", "Chat Studio");
      context.setVariable("verificationCode", captcha);
      context.setVariable("expireMinutes", 5);
      context.setVariable("year", LocalDateTime.now().getYear());
      String htmlContent = templateEngine.process("register-template", context);
      helper.setText(htmlContent, true);

      mailSender.send(message);

      notice.setSendTime(LocalDateTime.now());
      notice.setState(NoticeState.SUCCESS);
    } catch (MessagingException e) {
      log.error("send mail to {} error, cause: {}", email, e);
      notice.setExtendInfo(e.getClass().getName() + e.getMessage());
      notice.setState(NoticeState.FAILED);
    }
    noticeService.updateById(notice);
  }
}
