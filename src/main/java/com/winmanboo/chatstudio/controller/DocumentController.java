package com.winmanboo.chatstudio.controller;

import com.winmanboo.chatstudio.base.ReturnResult;
import com.winmanboo.chatstudio.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DocumentController {
  
  private final DocumentService documentService;
  
  @PostMapping("doc/upload/{kb_id}")
  public ReturnResult<Void> uploadDoc(@PathVariable("kb_id") Long kbId, @RequestParam("file") MultipartFile file)
      throws IOException {
    documentService.uploadDoc(kbId, file);
    return ReturnResult.success();
  }
}
