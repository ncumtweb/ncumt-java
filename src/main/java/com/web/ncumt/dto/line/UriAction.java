package com.web.ncumt.dto.line;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代表 LINE API 中的 URI 動作，通常用於按鈕的點擊事件。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UriAction {

    /**
     * 動作的類型，對於 URI 動作，此值固定為 "uri"。
     */
    private final String type = "uri";

    /**
     * 按鈕上顯示的文字。
     */
    private String label;

    /**
     * 點擊按鈕後要開啟的 URL。
     */
    private String uri;
}
