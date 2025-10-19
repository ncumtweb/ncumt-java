package com.web.ncumt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代表事件分類的枚舉。
 */
@Getter
@AllArgsConstructor
public enum EventCategory {
    /**
     * 出隊
     */
    OUTING(0, "出隊 | ", "#A8D8B9"),
    /**
     * 溯溪
     */
    RIVER(1, "溯溪 | ", "#7DB9DE"),
    /**
     * 社課
     */
    COURSE(2, "社課 | ", "#E6E5A3"),
    /**
     * 討論
     */
    DISCUSSION(3, "討論 | ", "#9B90C2"),
    /**
     * 山防
     */
    MOUNTAIN_DEFENSE(4, "山防 | ", "#E87A90");

    /**
     * 分類的索引。
     */
    private final int categoryIndex;
    /**
     * 標題前綴。
     */
    private final String prefix;
    /**
     * 在日曆上顯示的顏色。
     */
    private final String color;

    /**
     * 根據分類索引取得 EventCategory。
     * @param categoryIndex 分類索引
     * @return 對應的 EventCategory
     * @throws IllegalArgumentException 如果找不到對應的分類索引
     */
    public static EventCategory getByCategoryIndex(int categoryIndex) {
        for (EventCategory eventCategory : values()) {
            if (eventCategory.getCategoryIndex() == categoryIndex) {
                return eventCategory;
            }
        }
        throw new IllegalArgumentException("Invalid category index: " + categoryIndex);
    }
}
