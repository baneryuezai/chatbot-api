package com.dogG.chatbot.api;

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
import org.junit.Test;

import java.io.IOException;

/**
 *  测试获取问题、回答问题
 */
public class ApiTest {

    /**
     * 查询问题
     * @throws IOException
     */
    @Test
    public void query_unanswered_questions() throws IOException {
//        ===============   准备阶段
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        一般人家的路径后面的参数不要改，怕后台风控发现给你封掉——scope=unanswered_questions&count=20，她说20就20
        HttpGet httpGet=new HttpGet("https://api.zsxq.com/v2/groups/88885511211882/topics?scope=unanswered_questions&count=20");
//        cookie 可以在网络->请求——>标头——>复制cookie
        httpGet.addHeader("cookie","__cuid=10e8ce7f428c46c6b39344a6a1f86d08; amp_fef1e8=a22c9b63-ab1f-4401-aaf7-766b094df846R...1go8ekenc.1go8ekeng.1.1.2; zsxqsessionid=e74d810aba3c61c6a0c7937a1910b803; abtest_env=product; UM_distinctid=1861104827875-007ca0e66bc9ac-7d5d5474-1bcab9-18611048279102b; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"841115428542142\",\"first_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2MTEwNDg1ZDYxMjkxLTA3MGJjYzk2ZGM2NDRiLTdkNWQ1NDc0LTE4MjEzNjktMTg2MTEwNDg1ZDcxNWI4IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODQxMTE1NDI4NTQyMTQyIn0=\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"841115428542142\"},\"$device_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\"}; zsxq_access_token=173892A8-7754-5956-F3C5-38E1F4C99471_1B7DF7ADDBC0FF82");
        httpGet.addHeader("Content-Type","application/json; charset=UTF-8");
//        ================  发送请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);
            System.out.println(resp);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    /**
     * 返回结果
     * @throws IOException
     */
    @Test
    public void answer_unanswered_questions() throws IOException {
//        ===============   准备阶段
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        一般“人家网站”的路径后面的参数不要改，怕后台风控发现给你封掉——scope=unanswered_questions&count=20，她说20就20
        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/214882154228811/answer");
//        cookie 可以在网络->请求——>标头——>复制cookie
//        httpPost.addHeader("cookie","__cuid=10e8ce7f428c46c6b39344a6a1f86d08; amp_fef1e8=a22c9b63-ab1f-4401-aaf7-766b094df846R...1go8ekenc.1go8ekeng.1.1.2; zsxq_access_token=3512613A-C7A6-0646-F799-B869D08A79BF_1B7DF7ADDBC0FF82; zsxqsessionid=e74d810aba3c61c6a0c7937a1910b803; abtest_env=product; UM_distinctid=1861104827875-007ca0e66bc9ac-7d5d5474-1bcab9-18611048279102b; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"841115428542142\",\"first_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2MTEwNDg1ZDYxMjkxLTA3MGJjYzk2ZGM2NDRiLTdkNWQ1NDc0LTE4MjEzNjktMTg2MTEwNDg1ZDcxNWI4IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODQxMTE1NDI4NTQyMTQyIn0=\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"841115428542142\"},\"$device_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\"}");
        httpPost.addHeader("Content-Type","application/json; charset=UTF-8");
        httpPost.addHeader("cookie","__cuid=10e8ce7f428c46c6b39344a6a1f86d08; amp_fef1e8=a22c9b63-ab1f-4401-aaf7-766b094df846R...1go8ekenc.1go8ekeng.1.1.2; zsxqsessionid=e74d810aba3c61c6a0c7937a1910b803; abtest_env=product; UM_distinctid=1861104827875-007ca0e66bc9ac-7d5d5474-1bcab9-18611048279102b; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={\"distinct_id\":\"841115428542142\",\"first_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\",\"props\":{},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2MTEwNDg1ZDYxMjkxLTA3MGJjYzk2ZGM2NDRiLTdkNWQ1NDc0LTE4MjEzNjktMTg2MTEwNDg1ZDcxNWI4IiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODQxMTE1NDI4NTQyMTQyIn0=\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"841115428542142\"},\"$device_id\":\"186110485d61291-070bcc96dc644b-7d5d5474-1821369-186110485d715b8\"}; zsxq_access_token=173892A8-7754-5956-F3C5-38E1F4C99471_1B7DF7ADDBC0FF82");

//        ================  封装返回参数   silenced 为 false的话就是所有人可见
        String paramJon="{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"当然可以！我现在正在学，学会了就教你哈\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";
//        =================  定义发送的请求体(包装成json,网络发送和接收数据都是json/报文的形式)
        StringEntity stringEntity = new StringEntity(paramJon, ContentType.create("text/json","UTF-8"));
        httpPost.setEntity(stringEntity);
//        ================  发送返回请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);
            System.out.println(resp);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    /**
     * 测试调用chatGPT接口
     */
    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://api.openai.com/v1/completions");
        httpPost.addHeader("Content-Type","application/json");
        httpPost.addHeader("Authorization","Bearer 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");

        String params ="{\"model\": \"text-davinci-003\", \"prompt\": \"chatGPT能创造出什么商业价值呢？\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(params, ContentType.create("text/json","UTF-8"));
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);
            System.out.println(resp);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
