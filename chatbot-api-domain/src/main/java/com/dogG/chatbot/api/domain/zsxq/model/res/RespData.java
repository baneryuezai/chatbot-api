package com.dogG.chatbot.api.domain.zsxq.model.res;

import com.dogG.chatbot.api.domain.zsxq.model.vo.Topics;

import java.util.List;

public class RespData
{
    private List<Topics> topics;

    public void setTopics(List<Topics> topics){
        this.topics = topics;
    }
    public List<Topics> getTopics(){
        return this.topics;
    }

}