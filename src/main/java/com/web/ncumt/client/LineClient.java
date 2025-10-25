package com.web.ncumt.client;

import com.web.ncumt.dto.ApiResponse;
import com.web.ncumt.dto.line.LinePushRequest;
import com.web.ncumt.dto.line.LineReplyResponse;
import com.web.ncumt.dto.line.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class LineClient {

    private static final String LINE_API_URL = "https://api.line.me/v2/bot/message/push";

    @Autowired
    private HttpClient httpClient;

    @Value("${line.bot.channel-token}")
    private String channelToken;

    @Value("${line.bot.user-id}")
    private String userId;

    /**
     * 發送一個 Message 到 LINE。
     *
     * @param message 要發送的 Message 物件。
     */
    public void sendMessage(Message message) {
        LinePushRequest request = new LinePushRequest(userId, Collections.singletonList(message));
        pushMessage(request);
    }

    /**
     * 執行實際的 push message API 呼叫。
     *
     * @param request 包含訊息內容的請求物件。
     */
    private void pushMessage(Object request) {
        Map<String, String> headers = new java.util.HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + channelToken);

        try {
            httpClient.post(LINE_API_URL, headers, request, LineReplyResponse.class);
        } catch (Exception e) {
            log.error("Error sending message to Line: ", e);
            new ApiResponse<>(-1, null, e.getMessage());
        }
    }
}
