package com.web.ncumt.dto.line;

import lombok.Data;

@Data
public class LineMessage implements Message {
    private final String type = "text";
    private String text;

    public LineMessage(String text) {
        this.text = text;
    }
}
