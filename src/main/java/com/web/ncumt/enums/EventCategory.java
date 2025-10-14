package com.web.ncumt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventCategory {
    OUTING(0, "出隊 | ", "#A8D8B9"),
    RIVER(1, "溯溪 | ", "#7DB9DE"),
    COURSE(2, "社課 | ", "#E6E5A3"),
    DISCUSSION(3, "討論 | ", "#9B90C2"),
    MOUNTAIN_DEFENSE(4, "山防 | ", "#E87A90");

    private final int categoryIndex;
    private final String prefix;
    private final String color;

    /**
     * Get EventCategory by category index
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
