# 基础镜像
FROM openjdk:8-jre-slim
# 作者
MAINTAINER fengleizhen
# 配置
ENV PARAMS="--chatbot-api.groupId=88885511211882 --chatbot-api.openAiKey=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 --chatbot-api.cookie=zsxq_access_token=BEB5B79B-0C33-F190-D0FA-89B1598A3A45_1B7DF7ADDBC0FF82; zsxqsessionid=058fbf61eba56e65ffe4b5a7d81942bd; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross={"distinct_id":"841115428542142","first_id":"18620b10ece983-04a5e40c1482128-7d5d547c-1821369-18620b10ecfc68","props":{},"identities":"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2MjBiMTBlY2U5ODMtMDRhNWU0MGMxNDgyMTI4LTdkNWQ1NDdjLTE4MjEzNjktMTg2MjBiMTBlY2ZjNjgiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI4NDExMTU0Mjg1NDIxNDIifQ==","history_login_id":{"name":"$identity_login_id","value":"841115428542142"},"$device_id":"18620b10ece983-04a5e40c1482128-7d5d547c-1821369-18620b10ecfc68"}"
# 时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 添加应用
#ADD /chatbot-api-interfaces/target/chatbot-api.jar ./chatbot-api.jar
ADD /chatbot-api-interfaces/target/chatbot-api.jar /chatbot-api.jar
# 执行镜像；docker run -e PARAMS=" --chatbot-api.groupId=你的星球ID --chatbot-api.openAiKey=自行申请 --chatbot-api.cookie=登录cookie信息" -p 8090:8090 --name chatbot-api -d chatbot-api:1.0
ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /chatbot-api.jar $PARAMS"]