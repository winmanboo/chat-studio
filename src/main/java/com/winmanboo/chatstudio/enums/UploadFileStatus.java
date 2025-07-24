package com.winmanboo.chatstudio.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UploadFileStatus {
  
  UPLOADING(0, "上传中"),
  
  ANALYZING(1, "解析中"),
  
  ANALYZED(2, "解析完成"),
  
  UPLOAD_FAILED(-1, "上传失败"),
  
  ANALYZE_FAILED(-2, "解析失败");
  
  private final int code;
  
  private final String description;
}
