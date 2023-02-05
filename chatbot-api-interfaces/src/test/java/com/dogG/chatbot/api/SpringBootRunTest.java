package com.dogG.chatbot.api;

import com.alibaba.fastjson.JSONObject;
import com.dogG.chatbot.api.domain.ai.IOpenAI;
import com.dogG.chatbot.api.domain.zsxq.IZsxqApi;
import com.dogG.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.dogG.chatbot.api.domain.zsxq.model.vo.Topics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {
    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Resource
    private IZsxqApi IZsxqApi;
    @Resource
    private IOpenAI IOpenAI;


    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    /**
     *  测试查询 知识星球中的  “待我回答”
     * @throws IOException
     */
    @Test
    public void testZsxqApiQuery() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = IZsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果：{}", JSONObject.toJSON(unAnsweredQuestionsAggregates));
        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            logger.info("topicId：{} text：{}", topicId, text);

//             回答问题
            IZsxqApi.answer(groupId, cookie, topicId, text, false);
        }
    }

    /**
     * 对接chatGPT API 接口
     */
    @Test
    public void testChatGPTApi() throws IOException {

        String responseJson = IOpenAI.doChatGPT("chatGPT能创造出什么商业价值呢？");
        logger.info("测试chatGPT回答的数据：{}",responseJson);
    }

    /**
     * @param groupId  星球上的请求带的
     * @param cookie   cookie
     * @param topicId  提问的问题id
     * @param text     提问的内容
     * @param silenced 是否可见
     * @return
     * @throws IOException
     */
    /**
     *   测试我回答的结果
     * @throws IOException
     */
    @Test
    public void testZsxqApiAnswer() throws IOException {
//        boolean answer = IZsxqApi.answer(groupId, cookie, topicId, text, silenced);
    }
}
