package com.dogG.chatbot.api.application.job;

import com.alibaba.fastjson.JSONObject;
import com.dogG.chatbot.api.domain.ai.IOpenAI;
import com.dogG.chatbot.api.domain.zsxq.IZsxqApi;
import com.dogG.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.dogG.chatbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

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

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);
//    @Scheduled(cron = "0/6 * * * * ?")
      @Scheduled(cron = "0/30 * * * * ?")
    public void run() throws IOException {
        try {
//        避免扫描知识星球波长一样，被风控抓到
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中...");
                return;
            }
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
            if (hour > 23 || hour < 7) {
                logger.info("打烊时间不工作，AI 下班了！");
                return;
            }
//            1.查询知识星球的问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = IZsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            logger.info("查询知识星球问题的结果：{}", JSONObject.toJSON(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (null == topics || topics.isEmpty()) {
                logger.info("本次检索未找到待回答数据");
                return;
            }
//            2.调用chatGPT接口查询回答出的答案
            Topics topic = topics.get(0);   // 一条一条回答
            String answer = IOpenAI.doChatGPT(topic.getQuestion().getText());  //chatGPT 回答结果
//            3.把结果封装进行返回
//            String text = topics.get(0).getQuestion().getText();  // 输入到chatGPT的问题
            IZsxqApi.answer(groupId, cookie, topic.getTopic_id(), answer, false);
            logger.info("编号：{} 问题：{} 回答：{} ", topic.getTopic_id(), topic.getQuestion().getText(), "chatGPT回答："+"\n" + answer);
        }catch(Exception e){
            logger.info("自动回答异常",e);
        }
    }

}
