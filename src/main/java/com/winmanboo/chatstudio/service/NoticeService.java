package com.winmanboo.chatstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.winmanboo.chatstudio.entity.Notice;

/**
 * @author boo
 * @date 2025/7/25 14:04
 */
public interface NoticeService extends IService<Notice> {

  Notice saveCaptchaNotice(String email, String captcha);
}
