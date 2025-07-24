package com.winmanboo.chatstudio.utils;

import cn.hutool.core.io.FileUtil;
import com.winmanboo.chatstudio.config.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class OssUtil {
  
  private final OssProperties ossProperties;
  
  public String buildDefaultObjectKey(File file) {
    return buildObjectKey(file, ossProperties.getDefaultPathStyleAccess(), ossProperties.getDefaultObjectKeyFormat());
  }
  
  public String buildDocObjectKey(String prefix, String suffix) {
    OssProperties.Doc doc = ossProperties.getDoc();
    return buildObjectKey(prefix, suffix, doc.getPathStyleAccess(), doc.getObjectKeyFormat());
  }
  
  public String buildObjectKey(File file, String pathStyleAccess, String objectKeyFormat) {
    String prefix = FileUtil.getPrefix(file);
    String suffix = FileUtil.getSuffix(file);
    return buildObjectKey(prefix, suffix, pathStyleAccess, objectKeyFormat);
  }
  
  public String buildObjectKey(String prefix, String suffix, String pathStyleAccess, String objectKeyFormat) {
    return pathStyleAccess + "/" + String.format(objectKeyFormat, prefix, System.currentTimeMillis(), suffix);
  }
}
