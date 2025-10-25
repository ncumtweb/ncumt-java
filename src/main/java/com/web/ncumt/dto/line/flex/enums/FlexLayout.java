package com.web.ncumt.dto.line.flex.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FlexLayout {
    VERTICAL("vertical"),
    HORIZONTAL("horizontal"),
    BASELINE("baseline");

    private final String value;

    FlexLayout(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
