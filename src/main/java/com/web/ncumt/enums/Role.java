package com.web.ncumt.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 使用者角色的枚舉類型。
 * 每個角色都對應一個整數值和一個中文顯示名稱。
 */
@Getter
public enum Role {
    NON_MEMBER(-1, "非社員"),
    MEMBER(0, "社員"),
    CLUB_LEADER(1, "社長"),
    VICE_CLUB_LEADER(2, "副社長"),
    GUIDE_LEADER(3, "嚮導組組長"),
    GUIDE_MEMBER(4, "嚮導組組員"),
    TECH_LEADER(5, "技術組組長"),
    TECH_MEMBER(6, "技術組組員"),
    EQUIPMENT_LEADER(7, "器材組組長"),
    EQUIPMENT_MEMBER(8, "器材組組員"),
    MEDICAL_LEADER(9, "醫藥組組長"),
    MEDICAL_MEMBER(10, "醫藥組組員"),
    SECRETARY_LEADER(11, "文書組組長"),
    SECRETARY_MEMBER(12, "文書組組員"),
    DESIGN(13, "美宣"),
    WEB_ADMIN(14, "網管"),
    TREASURER(15, "財務長"),
    SAFETY_LEADER(16, "山防組組長"),
    SAFETY_MEMBER(17, "山防組組員");

    private final int value;
    private final String displayName;

    Role(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * 根據整數值查找對應的 Role。
     *
     * @param value 角色的整數值
     * @return 對應的 Role，如果找不到則預設為 NON_MEMBER。
     */
    public static Role fromValue(Integer value) {
        if (value == null) {
            return NON_MEMBER;
        }
        return Arrays.stream(Role.values())
                .filter(role -> role.getValue() == value)
                .findFirst()
                .orElse(NON_MEMBER);
    }

    /**
     * 是否有管理權限
     */
    public boolean isAdmin() {
        return value > MEMBER.getValue();
    }

    /**
     * 是否為社員
     */
    public boolean isClubMember() {
        return value >= MEMBER.getValue();
    }
}
