package com.winmanboo.chatstudio.service;

import com.winmanboo.chatstudio.domain.SessionVO;
import com.winmanboo.chatstudio.entity.Message;

import java.util.List;

public interface ChatService {

  String create();

  List<SessionVO> sessions();

  List<Message> messages(String sessionId);

  void deleteSession(String sessionId);
}
