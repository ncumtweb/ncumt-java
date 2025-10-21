package com.web.ncumt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 公告類型的枚舉。
 * 每個類型都包含一個顯示名稱和一個對應的顏色代碼。
 */
@AllArgsConstructor
@Getter
public enum PostType {
    TEAM(0, "隊伍", "#A8D8B9"),
    COURSE(1, "社課", "#E6E5A3"),
    OTHER(2, "其他", "#9B90C2");

    private final int value;
    private final String typeName;
    private final String color;

    /**
     * 根據整數值查找對應的 PostType。
     *
     * @param value 類型的值
     * @return 對應的 PostType，如果找不到則返回 OTHER。
     */
    public static PostType fromValue(Integer value) {
        if (value == null) {
            return OTHER;
        }
        return Arrays.stream(PostType.values())
                .filter(type -> type.getValue() == value)
                .findFirst()
                .orElse(OTHER);
    }
}
