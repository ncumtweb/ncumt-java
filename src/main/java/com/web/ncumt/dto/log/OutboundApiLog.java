package com.web.ncumt.dto.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class OutboundApiLog {
    private String route;
    private String requestBody;
    private Integer responseStatus;
    private String responseBody;
    private String errorBody;
    private String errorMessage;

    /**
     * 根據日誌物件的當前狀態，記錄最終的日誌訊息。
     * 如果包含錯誤訊息或 HTTP 狀態碼為 400 及以上，則記錄為 ERROR，否則記錄為 INFO。
     */
    public void logFinalStatus() {
        if (this.getErrorMessage() != null || (this.getResponseStatus() != null && this.getResponseStatus() >= 400)) {
            log.error("Outbound API call finished with error");
        } else {
            log.info("Outbound API call finished successfully");
        }
    }

    /**
     * 將此物件的所有非空欄位填充到 MDC 中，以便進行結構化日誌記錄。
     *
     * @param objectMapper 用於將物件轉換為 Map 的 Jackson ObjectMapper
     */
    public void populateMdcWithFields(ObjectMapper objectMapper) {
        MDC.put("log_type", "outboundApi");
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> fields = objectMapper.convertValue(this, Map.class);

            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                if (entry.getValue() != null) {
                    MDC.put(entry.getKey(), entry.getValue().toString());
                }
            }
        } catch (Exception e) {
            log.error("Failed to populate MDC from OutboundApiLog", e);
        }
    }
}
