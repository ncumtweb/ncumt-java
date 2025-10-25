package com.web.ncumt.dto.line;

import lombok.Data;

@Data
public class LineMessage implements Message { // 實作 Message 介面
    private final String type = "text";
    private String text;

    public LineMessage(String text) {
        this.text = text;
    }
}
