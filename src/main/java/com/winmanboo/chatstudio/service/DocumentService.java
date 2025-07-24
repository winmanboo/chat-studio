package com.winmanboo.chatstudio.service;

import dev.langchain4j.model.output.TokenUsage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {
  
  void uploadDoc(Long kbId, MultipartFile file) throws IOException;
  
}
