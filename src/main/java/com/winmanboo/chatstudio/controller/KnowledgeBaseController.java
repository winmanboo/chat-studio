package com.winmanboo.chatstudio.controller;

import com.winmanboo.chatstudio.base.ReturnResult;
import com.winmanboo.chatstudio.context.UserInfoContext;
import com.winmanboo.chatstudio.domain.KbItemVO;
import com.winmanboo.chatstudio.service.KbStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/kb")
@RequiredArgsConstructor
public class KnowledgeBaseController {
  
  private final KbStoreService kbStoreService;
  
  @GetMapping("/list")
  public ReturnResult<List<KbItemVO>> list() {
    return ReturnResult.success(kbStoreService.getKbItemList(UserInfoContext.get().getUserId()));
  }
  
  @DeleteMapping("/delete/{itemId}")
  public ReturnResult<Void> delete(@PathVariable Long itemId) {
    kbStoreService.removeById(itemId);
    return ReturnResult.success();
  }
}
