package com.winmanboo.chatstudio.ai.langchain;

import com.winmanboo.chatstudio.ai.langchain.store.MongoChatMemoryStore;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.memory.ChatMemory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static dev.langchain4j.internal.ValidationUtils.ensureGreaterThanZero;
import static dev.langchain4j.internal.ValidationUtils.ensureNotNull;

@Slf4j
public class AdvancedMessageWindowChatMemory implements ChatMemory {

  private final Object id;

  private final Integer maxMessages;

  private final MongoChatMemoryStore store;

  private AdvancedMessageWindowChatMemory(Builder builder) {
    this.id = ensureNotNull(builder.id, "id");
    this.maxMessages = ensureGreaterThanZero(builder.maxMessages, "maxMessages");
    this.store = ensureNotNull(builder.store, "store");
  }

  @Override
  public Object id() {
    return id;
  }

  @Override
  public void add(ChatMessage message) {
    if (message instanceof SystemMessage) {
      return;
    }

    store.updateMessages(id, List.of(message));
  }

  @Override
  public List<ChatMessage> messages() {
    if (maxMessages <= 0) {
      return store.getMessages(id);
    }
    return store.getMessages(id, maxMessages);
  }

  @Override
  public void clear() {
    store.deleteMessages(id);
  }

  public static Builder builder(Object id) {
    return new Builder(id);
  }

  public static class Builder {
    private Object id;

    private Integer maxMessages;

    private MongoChatMemoryStore store;

    public Builder(Object id) {
      this.id = id;
    }

    public Builder id(Object id) {
      this.id = id;
      return this;
    }

    public Builder maxMessages(Integer maxMessages) {
      this.maxMessages = maxMessages;
      return this;
    }

    public Builder store(MongoChatMemoryStore store) {
      this.store = store;
     return this;
    }

    public AdvancedMessageWindowChatMemory build() {
      return new AdvancedMessageWindowChatMemory(this);
    }
  }
}
