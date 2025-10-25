package com.web.ncumt.dto.line.flex.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FlexSpacing {
    NONE("none"),
    XS("xs"),
    SM("sm"),
    MD("md"),
    LG("lg"),
    XL("xl"),
    XXL("xxl");

    private final String value;

    FlexSpacing(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
