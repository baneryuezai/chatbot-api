package com.dogG.chatbot.api.domain.zsxq;

import com.dogG.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * 知识星球API
 */
public interface IZsxqApi {
    /**
     *
     * @param groupId groupId 星球上的请求带的
     * @param cookie
     * @return
     * @throws IOException
     */
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    /**
     *
     * @param groupId   星球上的请求带的
     * @param cookie    cookie
     * @param topicId   提问的问题id
     * @param text      提问的内容
     * @param silenced  是否可见
     * @return
     * @throws IOException
     */
    boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
}
