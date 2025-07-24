package com.winmanboo.chatstudio.ai.promt;

import dev.langchain4j.model.input.PromptTemplate;

public class InternalPrompt {

    // 总结用户的查询
    public static final PromptTemplate DEFAULT_TRANSFORM_PROMPT_TEMPLATE = PromptTemplate.from("""
      阅读并了解用户与AI之间的对话， \
      然后，分析用户的新查询， \
      从对话和新查询中确定所有相关的细节，条款和上下文， \
      将该查询重新定为适合信息检索的清晰，简洁和独立的格式。
      
      对话：
      {{chatMemory}}
      
      用户查询：{{query}}
      
      非常重要的是，您只提供重新汇总的查询，而别无其他！ \
      不要用任何东西预先查询！""");

    public static final PromptTemplate DEFAULT_INJECTOR_PROMPT_TEMPLATE = PromptTemplate.from("""
      用户查询：{{userMessage}}
      
      如果用户查询中指定了信息，请优先使用以下信息进行回答，如果没有指定信息，则直接按以下信息回答:
      {{contents}}""");

    // 思维链
    public static final PromptTemplate THINKING_CHAIN_INJECTOR_PROMPT_TEMPLATE = PromptTemplate.from("""
      请逐步思考并解释：{{userMessage}}""");

    // 思维链 + 检索
    public static final PromptTemplate THINKING_BOTH_RETRIEVAL_PROMPT_TEMPLATE = PromptTemplate.from("""
      用户查询：{{userMessage}}
      
      已有信息如下：
      {{contents}}
      
      请逐步思考并解释用户查询，然后根据你思考的过程和已有的信息回答用户""");
}
