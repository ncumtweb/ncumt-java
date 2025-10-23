package com.web.ncumt.constant;

import java.time.format.DateTimeFormatter;

/**
 * 集中管理日期時間格式化常數的類別。
 */
public final class DateTimeFormatConstant {

    /**
     * 標準的日期時間格式：年-月-日 時:分 (yyyy-MM-dd HH:mm)。
     */
    public static final DateTimeFormatter YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * 私有建構子，防止這個工具類別被實例化。
     */
    private DateTimeFormatConstant() {
    }
}
