package com.winmanboo.chatstudio.event;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.event.AiServiceRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author winmanboo
 * @date 2025/3/22 17:13
 */
@Slf4j
@Component
public class AiServiceRegisteredEventListener implements ApplicationListener<AiServiceRegisteredEvent> {
    
    @Override
    public void onApplicationEvent(@NonNull AiServiceRegisteredEvent event) {
        Class<?> aiServiceClass = event.aiServiceClass();
        List<ToolSpecification> toolSpecifications = event.toolSpecifications();
        for (int i = 0; i < toolSpecifications.size(); i++) {
            log.info("[{}]: [Tool-{}]: {}", aiServiceClass.getSimpleName(), i + 1, toolSpecifications.get(i));
        }
    }
}
