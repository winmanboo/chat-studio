package com.winmanboo.chatstudio.service.impl;

import com.winmanboo.chatstudio.domain.RegisterDTO;
import com.winmanboo.chatstudio.entity.Notice;
import com.winmanboo.chatstudio.exception.BizException;
import com.winmanboo.chatstudio.limiter.SlideWindowRateLimiter;
import com.winmanboo.chatstudio.service.AuthService;
import com.winmanboo.chatstudio.service.MailService;
import com.winmanboo.chatstudio.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.winmanboo.chatstudio.constants.NoticeConstant.CAPTCHA_KEY_PREFIX;
import static com.winmanboo.chatstudio.exception.code.BizErrorCode.SEND_CODE_LIMIT;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final SlideWindowRateLimiter slideWindowRateLimiter;

  private final StringRedisTemplate stringRedisTemplate;

  private final NoticeService noticeService;

  private final MailService mailService;

  @Override
  public void register(RegisterDTO registerDTO) {

  }

  @Override
  public void generateMailCaptcha(String email) {
    Boolean pass = slideWindowRateLimiter.tryAcquire(email, 1, Duration.ofSeconds(60));

    if (!pass) {
      throw new BizException(SEND_CODE_LIMIT);
    }

    String captcha = RandomStringUtils.secure().nextNumeric(6);
    stringRedisTemplate.opsForValue().set(CAPTCHA_KEY_PREFIX + email, captcha, Duration.ofMinutes(5));

    Notice notice = noticeService.saveCaptchaNotice(email, captcha);
    mailService.sendCode(email, captcha, notice);
  }
}
