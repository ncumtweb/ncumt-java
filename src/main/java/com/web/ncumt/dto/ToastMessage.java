package com.web.ncumt.dto;

import com.web.ncumt.enums.ToastMessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代表一則 Toast 提示訊息的資料結構。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToastMessage {

    /**
     * 訊息的類型，例如 SUCCESS, ERROR 等。
     */
    private ToastMessageType type;

    /**
     * 訊息的具體內容。
     */
    private String message;
}
