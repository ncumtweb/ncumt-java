package com.web.ncumt.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ncumt.dto.ApiResponse;
import com.web.ncumt.dto.log.OutboundApiLog;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 通用的 HTTP 客戶端元件，負責處理向外的 POST 請求。
 * <p>
 * 此客戶端集成了結構化日誌功能，會自動記錄每次 API 呼叫的詳細資訊，
 * 包括請求、回應、狀態碼和任何可能發生的錯誤，並將這些資訊填充到 MDC (Mapped Diagnostic Context)
 * 中，以便日誌系統（如 ELK Stack）進行採集和分析。
 */
@Component
@Slf4j
public class HttpClient {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 發送一個 HTTP POST 請求。
     *
     * <p>此方法會處理請求的完整生命週期，包括建立連線、發送請求主體、解析回應，
     * 並在操作結束時（無論成功或失敗）記錄詳細的結構化日誌。
     *
     * @param <T>          預期的回應主體反序列化後的類型。
     * @param urlString    目標 API 的完整 URL 字串。
     * @param headers      一個包含請求標頭的 Map。
     * @param requestBody  要發送的請求主體物件，將被序列化為 JSON 字串。
     * @param responseType 預期回應的 Class 類型，用於 JSON 反序列化。
     * @return 一個 {@link ApiResponse} 物件，其中包含了 HTTP 狀態碼和反序列化後的回應主體（如果成功），
     * 或錯誤資訊（如果失敗）。
     */
    public <T> ApiResponse<T> post(String urlString, Map<String, String> headers, Object requestBody, Class<T> responseType) {
        HttpURLConnection conn = null;
        OutboundApiLog outboundLog = new OutboundApiLog();
        outboundLog.setRoute(urlString);
        ApiResponse<T> apiResponse;

        try {
            if (requestBody != null) {
                String jsonBody = objectMapper.writeValueAsString(requestBody);
                outboundLog.setRequestBody(jsonBody);
            }

            URL url = new URI(urlString).toURL();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            if (outboundLog.getRequestBody() != null && !outboundLog.getRequestBody().isEmpty()) {
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = outboundLog.getRequestBody().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int code = conn.getResponseCode();
            outboundLog.setResponseStatus(code);

            if (code >= 200 && code < 300) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseBodyString = br.lines().collect(Collectors.joining(System.lineSeparator()));
                    outboundLog.setResponseBody(responseBodyString);
                    T responseBody = objectMapper.readValue(responseBodyString, responseType);
                    apiResponse = new ApiResponse<>(code, responseBody, null);
                }
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    String errorBody = br.lines().collect(Collectors.joining(System.lineSeparator()));
                    outboundLog.setErrorBody(errorBody);
                    apiResponse = new ApiResponse<>(code, null, errorBody);
                }
            }
        } catch (Exception e) {
            outboundLog.setErrorMessage(e.getMessage());
            apiResponse = new ApiResponse<>(-1, null, e.getMessage());
        } finally {
            // 將 OutboundApiLog 物件的所有欄位填充到 MDC 中
            outboundLog.populateMdcWithFields(objectMapper);

            // 根據最終狀態記錄日誌
            outboundLog.logFinalStatus();

            if (conn != null) {
                conn.disconnect();
            }
            // 清理 MDC，為下一個請求做準備
            MDC.clear();
        }
        return apiResponse;
    }
}
