package com.winmanboo.chatstudio.service;

import com.winmanboo.chatstudio.entity.Notice;
import jakarta.mail.MessagingException;

public interface MailService {

  void sendCode(String email, String captcha, Notice notice);
}
