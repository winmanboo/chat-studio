package com.winmanboo.chatstudio.ai.langchain.store;

import com.winmanboo.chatstudio.ai.langchain.query.SessionTitleQueryTransform;
import com.winmanboo.chatstudio.entity.Message;
import com.winmanboo.chatstudio.entity.Session;
import com.winmanboo.chatstudio.exception.ChatException;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.winmanboo.chatstudio.entity.Message.DEFAULT_START_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoChatMemoryStore implements ChatMemoryStore {
  
  private final MongoTemplate mongoTemplate;
  
  private final SessionTitleQueryTransform sessionTitleQueryTransform;
  
  @Override
  public List<ChatMessage> getMessages(Object memoryId) {
    Session session = mongoTemplate.findOne(Query.query(Criteria.where("sessionId").is(memoryId)), Session.class);
    if (session == null) {
      throw new ChatException("session not found");
    }
    return session.getMessages().stream().map(message -> {
      if (message.getMessageType() == Message.Type.USER.getValue()) {
        return UserMessage.userMessage(message.getMessage());
      } else if (message.getMessageType() == Message.Type.AI.getValue()) {
        return AiMessage.aiMessage(message.getMessage());
      }
      throw new RuntimeException("not supported other type right now");
    }).collect(Collectors.toList());
  }
  
  public List<ChatMessage> getMessages(Object memoryId, Integer maxMessages) {
    List<ChatMessage> messages = getMessages(memoryId);
    if (messages.size() <= maxMessages) {
      return messages;
    }
    return messages.subList(messages.size() - maxMessages, messages.size());
  }

  @Override
  public void updateMessages(Object memoryId, List<ChatMessage> messages) {
    if (messages.isEmpty()) {
      return;
    }
    
    ChatMessage message = messages.get(0);
    
    String prompt;
    if (message instanceof UserMessage) {
      prompt = ((UserMessage) message).singleText();
    } else if (message instanceof AiMessage) {
      prompt = ((AiMessage) message).text();
    } else {
      throw new RuntimeException("not supported other type right now");
    }
    
    Query sessionIdQuery = Query.query(Criteria.where("sessionId").is(memoryId));
    Session session = mongoTemplate.findOne(sessionIdQuery, Session.class);
    if (session == null) {
      throw new ChatException("session not found");
    }
    List<Message> sessionMessageList = session.getMessages();
    // 用户第一次在当前会话查询
    if (sessionMessageList.isEmpty()) {
      if (!(message instanceof UserMessage)) {
        throw new ChatException("first message must be user message");
      }
      // 更新标题
      Executors.newFixedThreadPool(1).submit(updateSessionTitle((UserMessage) message, memoryId));
      
      sessionMessageList.add(Message.userMessage(DEFAULT_START_ID, String.valueOf(memoryId), prompt, 0));
    } else {
      Message lastMessage = CollectionUtils.lastElement(sessionMessageList);
      assert lastMessage != null;
      Message insertMessage;
      if (message instanceof UserMessage) {
        insertMessage = Message.userMessage(lastMessage.getId() + 1, String.valueOf(memoryId), prompt, lastMessage.getId());
      } else if (message instanceof AiMessage) {
        insertMessage = Message.assistantMessage(lastMessage.getId() + 1, String.valueOf(memoryId), prompt, lastMessage.getId());
      } else {
        throw new RuntimeException("not supported other type right now");
      }
      sessionMessageList.add(insertMessage);
    }
    session.setMessages(sessionMessageList);
    mongoTemplate.updateFirst(Query.of(sessionIdQuery), Update.update("messages", sessionMessageList), Session.class);
  }
  
  @Override
  public void deleteMessages(Object memoryId) {
    mongoTemplate.updateFirst(Query.query(Criteria.where("sessionId").is(memoryId)),
        Update.update("messages", Collections.emptyList()), Session.class);
  }
  
  public Runnable updateSessionTitle(UserMessage message, Object memoryId) {
    return () -> {
      try {
        Collection<dev.langchain4j.rag.query.Query> transformedQueries =
            sessionTitleQueryTransform.transform(dev.langchain4j.rag.query.Query.from(message.singleText()));
        dev.langchain4j.rag.query.Query transformQuery = transformedQueries.iterator().next();
        mongoTemplate.updateFirst(Query.query(Criteria.where("sessionId").is(memoryId)),
            Update.update("title", transformQuery.text()), Session.class);
      } catch (Exception e) {
        log.error("update session title error", e);
      }
    };
  }
}
