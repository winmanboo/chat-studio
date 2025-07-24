package com.winmanboo.chatstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.winmanboo.chatstudio.entity.Kb;

public interface KbService extends IService<Kb> {
  
  boolean isKbExists(String userId, Long kbId);
}
