package com.dogG.chatbot.api.domain.ai;

import java.io.IOException;

public interface IOpenAI {
    String doChatGPT(String questionText) throws IOException;
}
