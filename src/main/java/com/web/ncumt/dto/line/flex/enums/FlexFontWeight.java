package com.web.ncumt.dto.line.flex.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FlexFontWeight {
    REGULAR("regular"),
    BOLD("bold");

    private final String value;

    FlexFontWeight(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
