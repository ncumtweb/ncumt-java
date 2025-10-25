package com.web.ncumt.dto.line.flex.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FlexButtonStyle {
    PRIMARY("primary"),
    SECONDARY("secondary"),
    LINK("link");

    private final String value;

    FlexButtonStyle(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
