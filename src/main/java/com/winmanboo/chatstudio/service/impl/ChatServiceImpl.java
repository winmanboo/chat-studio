package com.winmanboo.chatstudio.service.impl;

import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.sankuai.inf.leaf.service.SegmentService;
import com.winmanboo.chatstudio.ai.constant.AiConstant;
import com.winmanboo.chatstudio.converter.SessionConverter;
import com.winmanboo.chatstudio.domain.SessionVO;
import com.winmanboo.chatstudio.entity.Message;
import com.winmanboo.chatstudio.entity.Session;
import com.winmanboo.chatstudio.exception.ChatException;
import com.winmanboo.chatstudio.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

  private final SegmentService segmentService;

  private final MongoTemplate mongoTemplate;

  private final SessionConverter sessionConverter;

  @Override
  public String create() {
    Result result = segmentService.getId(AiConstant.CHAT_MEMORY_ID_TAG);
    if (result.getStatus() == Status.SUCCESS) {
      String memoryId = String.valueOf(result.getId());
      // 新增一个会话记录
      Session session = new Session(memoryId,
          AiConstant.DEFAULT_SESSION_TITLE, Collections.emptyList(), LocalDateTime.now(), LocalDateTime.now());
      mongoTemplate.insert(session);
      return memoryId;
    } else {
      throw new ChatException("创建会话失败");
    }
  }

  @Override
  public List<SessionVO> sessions() {
    Query query = new Query().with(Sort.by(Direction.DESC, "createdAt"));
    query.fields().exclude("messages");
    List<Session> sessions = mongoTemplate.find(query, Session.class);
    return sessionConverter.toVoList(sessions);
  }

  @Override
  public List<Message> messages(String sessionId) {
    Session session = mongoTemplate.findById(sessionId, Session.class);
    if (session == null) {
      throw new ChatException("session not exist.");
    }
    return session.getMessages()
        .stream().peek(message -> {
          if (message.getMessageType() == Message.Type.USER.getValue()) {
            String[] splitMessages = message.getMessage()
                .split("如果用户查询中指定了信息，请优先使用以下信息进行回答，如果没有指定信息，则直接按以下信息回答:");
            if (splitMessages.length == 0) {
              return;
            }
            String userMessage = splitMessages[0];
            String replaced = userMessage.replace("用户查询：", "").replace("\n", "");
            message.setMessage(replaced);
          }
        }).toList();
  }

  @Override
  public void deleteSession(String sessionId) {
    mongoTemplate.remove(Query.query(Criteria.where("sessionId").is(sessionId)), Session.class);
  }
}
