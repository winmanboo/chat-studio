package com.winmanboo.chatstudio.ai.langchain.query;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.transformer.QueryTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

@Component
@RequiredArgsConstructor
public class SessionTitleQueryTransform implements QueryTransformer {
  
  private static final PromptTemplate PROMPT_TEMPLATE = PromptTemplate.from("""
      理解用户查询：{{query}}
      
      生成一个表示当前会话的标题""");
  
  private final ChatModel chatModel;
  
  @Override
  public Collection<Query> transform(Query query) {
    Prompt prompt = createPrompt(query);
    String response = chatModel.chat(prompt.text());
    Query transformedQuery = Query.from(response);
    return singletonList(transformedQuery);
  }
  
  private Prompt createPrompt(Query query) {
    Map<String, Object> variables = new HashMap<>();
    variables.put("query", query.text());
    return PROMPT_TEMPLATE.apply(variables);
  }
}
