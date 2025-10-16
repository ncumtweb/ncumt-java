package com.web.ncumt.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 定義 Toast 提示訊息的類型。
 * <p>
 * 使用 Enum 可以確保類型安全，避免因手動輸入字串錯誤而導致的問題。
 * </p>
 */
public enum ToastMessageType {
    SUCCESS("success"),
    ERROR("error"),
    WARNING("warning"),
    INFO("info");

    private final String key;

    ToastMessageType(String key) {
        this.key = key;
    }

    /**
     * 當這個 Enum 被序列化為 JSON 時，返回其對應的小寫鍵名。
     * 這確保了前端 JavaScript 可以接收到預期的字串值 (e.g., "success")。
     *
     * @return 小寫的鍵名
     */
    @JsonValue
    public String getKey() {
        return key;
    }
}
