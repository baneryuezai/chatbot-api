package com.dogG.chatbot.api.application.job;

import com.dogG.chatbot.api.domain.ai.IOpenAI;
import com.dogG.chatbot.api.domain.zsxq.IZsxqApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@EnableScheduling
@Configuration
public class ChatbotSchedule {

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Resource
    private IZsxqApi IZsxqApi;
    @Resource
    private IOpenAI IOpenAI;
    public void run(){

    }

}
