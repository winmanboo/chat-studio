package com.winmanboo.chatstudio;

import com.sankuai.inf.leaf.plugin.annotation.EnableLeafServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableLeafServer
@SpringBootApplication
@MapperScan("com.winmanboo.chatstudio.mapper")
public class ChatStudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatStudioApplication.class, args);
    }

}
