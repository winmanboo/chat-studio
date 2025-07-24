package com.winmanboo.chatstudio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winmanboo.chatstudio.entity.Kb;
import com.winmanboo.chatstudio.mapper.KbMapper;
import com.winmanboo.chatstudio.service.KbService;
import org.springframework.stereotype.Service;

@Service
public class KbServiceImpl extends ServiceImpl<KbMapper, Kb> implements KbService {
  
  @Override
  public boolean isKbExists(String userId, Long kbId) {
    return lambdaQuery().eq(Kb::getUserId, userId).eq(Kb::getId, kbId).one() != null;
  }
}
