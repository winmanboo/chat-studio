package com.winmanboo.chatstudio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winmanboo.chatstudio.entity.Notice;
import com.winmanboo.chatstudio.enums.NoticeState;
import com.winmanboo.chatstudio.exception.BizException;
import com.winmanboo.chatstudio.mapper.NoticeMapper;
import com.winmanboo.chatstudio.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.winmanboo.chatstudio.enums.NoticeType.EMAIL;
import static com.winmanboo.chatstudio.constants.NoticeConstant.NOTICE_CAPTCHA_TITLE;
import static com.winmanboo.chatstudio.exception.code.BizErrorCode.NOTICE_SAVE_FAILED;

/**
 * @author boo
 * @date 2025/7/25 14:05
 */
@Slf4j
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

  @Override
  public Notice saveCaptchaNotice(String email, String captcha) {
    Notice notice = Notice.builder()
        .noticeTitle(NOTICE_CAPTCHA_TITLE)
        .noticeContent(captcha)
        .noticeType(EMAIL)
        .receiveAddress(email)
        .state(NoticeState.INIT)
        .build();

    if (!save(notice)) {
      throw new BizException(NOTICE_SAVE_FAILED);
    }
    return notice;
  }
}
