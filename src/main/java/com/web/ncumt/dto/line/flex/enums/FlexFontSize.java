package com.web.ncumt.dto.line.flex.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FlexFontSize {
    XXS("xxs"),
    XS("xs"),
    SM("sm"),
    MD("md"),
    LG("lg"),
    XL("xl"),
    XXL("xxl"),
    XXXL("3xl"),
    XXXXL("4xl"),
    XXXXXL("5xl");

    private final String value;

    FlexFontSize(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
