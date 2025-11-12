package com.web.ncumt.helper;

import com.web.ncumt.dto.line.UriAction;
import com.web.ncumt.dto.line.flex.FlexMessage;
import com.web.ncumt.dto.line.flex.component.Box;
import com.web.ncumt.dto.line.flex.component.Button;
import com.web.ncumt.dto.line.flex.component.FlexComponent;
import com.web.ncumt.dto.line.flex.component.Text;
import com.web.ncumt.dto.line.flex.container.Bubble;
import com.web.ncumt.dto.line.flex.enums.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 輔助產生 LINE Flex Message 的元件。
 */
@Component
public class FlexMessageHelper {

    /**
     * 根據給定的參數，建立一個結構化的例外告警 Flex Message。
     */
    public FlexMessage createExceptionAlert(String title, String exceptionType, String message, String requestId, String kibanaLink) {
        // Header
        Box header = Box.builder().layout(FlexLayout.VERTICAL).contentList(Collections.singletonList(
                        Text.builder().text(title).weight(FlexFontWeight.BOLD).size(FlexFontSize.MD).build()
                ))
                .build();

        // Body - 動態建立內容，現在是垂直排列且可複製
        List<FlexComponent> bodyContents = new ArrayList<>();
        bodyContents.addAll(createKeyValueComponent("Type", exceptionType));
        bodyContents.addAll(createKeyValueComponent("Message", message));
        if (requestId != null) {
            bodyContents.addAll(createKeyValueComponent("Request ID", requestId));
        }
        Box body = Box.builder().layout(FlexLayout.VERTICAL).contentList(bodyContents)
                .spacing(FlexSpacing.MD) // 設定元件之間的間距
                .build();

        // Footer - 只有在 kibanaLink 存在時才建立
        Box footer = null;
        if (kibanaLink != null) {
            footer = Box.builder().layout(FlexLayout.VERTICAL).contentList(Collections.singletonList(
                            Button.builder()
                                    .style(FlexButtonStyle.SECONDARY)
                                    .action(new UriAction("🔍 View Logs in Kibana", kibanaLink))
                                    .build()
                    ))
                    .build();
        }

        // Bubble
        Bubble bubble = Bubble.builder()
                .header(header)
                .body(body)
                .footer(footer)
                .build();

        // Flex Message
        return new FlexMessage(title, bubble);
    }

    /**
     * 建立一個垂直排列的 "Key" 和 "Value" 文字元件列表，讓內容可以被輕易複製。
     *
     * @param key   標題文字。
     * @param value 內容文字，如果為 null，會顯示為 "N/A"。
     * @return 一個包含兩個 Text 元件的 List。
     */
    private List<FlexComponent> createKeyValueComponent(String key, String value) {
        return List.of(
                Text.builder()
                        .text(key)
                        .weight(FlexFontWeight.BOLD)
                        .size(FlexFontSize.SM)
                        .build(),
                Text.builder()
                        .text(value != null ? value : "N/A")
                        .wrap(true)
                        .size(FlexFontSize.SM)
                        .build()
        );
    }
}
