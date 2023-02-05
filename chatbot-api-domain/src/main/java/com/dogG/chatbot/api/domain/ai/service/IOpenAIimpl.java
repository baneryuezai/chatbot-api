package com.dogG.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.dogG.chatbot.api.domain.ai.IOpenAI;
import com.dogG.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.dogG.chatbot.api.domain.ai.model.vo.Choices;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IOpenAIimpl implements IOpenAI {
    @Value("${chatbot-api.openApiKey}")
    private String apikey;
    private Logger logger = LoggerFactory.getLogger(IOpenAIimpl.class);

    @Override
    public String doChatGPT(String questionText) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/completions");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization", "Bearer " + apikey);

        String params = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + questionText + "\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(params, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);
            // 封装了回来的信息，因为可能回来一段一段的 这里就简写了，直接返回了一个
            AIAnswer aiAnswer = JSON.parseObject(resp, AIAnswer.class);
            StringBuilder stringBuilder = new StringBuilder();
            for (Choices choice : aiAnswer.getChoices()) {
                stringBuilder.append(choice.getText());
            }
            logger.info("chatGPT返回数据信息：{}", resp);
            return stringBuilder.toString();
        } else {
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
