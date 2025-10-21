package com.web.ncumt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 登山紀錄分類的枚舉類型。
 */
@AllArgsConstructor
@Getter
public enum RecordCategory {
    INTERMEDIATE_MOUNTAIN((short) 0, "中級山"),
    HIGH_MOUNTAIN((short) 1, "高山"),
    RIVER_TRACING((short) 2, "溯溪");

    private final short value;
    private final String name;

    /**
     * 根據數值查找對應的 RecordCategory。
     *
     * @param value 分類的值
     * @return 對應的 RecordCategory，如果找不到則預設為 INTERMEDIATE_MOUNTAIN。
     */
    public static RecordCategory fromValue(Short value) {
        if (value == null) {
            return INTERMEDIATE_MOUNTAIN;
        }
        return Arrays.stream(RecordCategory.values())
                .filter(category -> category.getValue() == value)
                .findFirst()
                .orElse(INTERMEDIATE_MOUNTAIN);
    }
}
