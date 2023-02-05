package com.dogG.chatbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dogG.chatbot.api.domain.zsxq.IZsxqApi;
import com.dogG.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import com.dogG.chatbot.api.domain.zsxq.model.req.AnswerReq;
import com.dogG.chatbot.api.domain.zsxq.model.req.ReqData;
import com.dogG.chatbot.api.domain.zsxq.model.res.AnswerRes;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class IZsxqImpl implements IZsxqApi {
    private Logger logger = LoggerFactory.getLogger(IZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        //        ===============   准备阶段
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        一般人家的路径后面的参数不要改，怕后台风控发现给你封掉——scope=unanswered_questions&count=20，她说20就20
        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");
//        cookie 可以在网络->请求——>标头——>复制cookie
        httpGet.addHeader("cookie", cookie);
        httpGet.addHeader("Content-Type", "application/json; charset=UTF-8");
//        ================  发送请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String jsonStr = EntityUtils.toString(entity);
            logger.info("拉取提问数据。groupId:{} jsonStr:{}",groupId,jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is" + response.getStatusLine().getStatusCode());
        }

    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {

        //        ===============   准备阶段
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        一般“人家网站”的路径后面的参数不要改，怕后台风控发现给你封掉——scope=unanswered_questions&count=20，她说20就20
        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");
//        cookie 可以在网络->请求——>标头——>复制cookie
//        httpPost.addHeader("cookie","__cuid=10e8ce7f428c46c6b39344a6a1f86d08; amp_fef1e8=a22c9b63-ab1f-4401-aaf7-766b094df846R...1go8ekenc.1go8ekeng.1.1.2; zsxq_access_token=3512613A-C7A6-0646-F799-B869D08A79BF_1B7DF7ADDBC0FF82; zsxqsessionid=e74d810aba3c61c6a0c7937a1910b803; abtest_env=product; UM_distinctid=1861104827875-007ca0e66bc9ac-7d5d5474-1bcab9-18611048279102b; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"841115428542142\",\"first_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2MTEwNDg1ZDYxMjkxLTA3MGJjYzk2ZGM2NDRiLTdkNWQ1NDc0LTE4MjEzNjktMTg2MTEwNDg1ZDcxNWI4IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODQxMTE1NDI4NTQyMTQyIn0=\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"841115428542142\"},\"$device_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\"}");
        httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.addHeader("cookie", cookie);
//          防止风控，告诉他我这个是从浏览器获取的
        httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 Edg/109.0.1518.70");
//        ================  封装返回参数   silenced 为 false的话就是所有人可见
//        测试数据
        /*String paramJon="{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"当然可以！我现在正在学，学会了就教你哈\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";*/
//        ==================


//        =================  定义返回的请求体
        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        String paramJson = JSONObject.toJSONString(answerReq);// 转换对象
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(stringEntity);
//        ================  发送返回请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String jsonStr = EntityUtils.toString(entity);
            logger.info("回答提问数据。groupId:{} topicId:{} jsonStr:{}",groupId,topicId,jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("Answer Err is" + response.getStatusLine().getStatusCode());
        }
    }
}
