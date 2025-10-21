package com.web.ncumt.constant;

/**
 * 這個類別存放了在 Spring MVC 的 Model 中常用的屬性名稱（鍵）。
 * 使用這個類別可以避免在程式碼中直接使用字串（"magic strings"），
 * 從而提高程式碼的可讀性和可維護性。
 */
public final class ModelAttributeConstant {

    /**
     * 用於存放當前登入使用者的鍵名。
     */
    public static final String CURRENT_LOGIN_USER = "currentLoginUser";
    /**
     * 判斷是否為管理員的鍵名。
     */
    public static final String IS_ADMIN = "isAdmin";
    /**
     * 判斷是否為社員的鍵名。
     */
    public static final String IS_CLUB_MEMBER = "isClubMember";
    /**
     * 用於存放活動紀錄列表的鍵名。
     */
    public static final String RECORD_LIST = "recordList";
    /**
     * 用於存放頁面標題的鍵名。
     */
    public static final String PAGE_TITLE = "pageTitle";
    /**
     * 用於存放日曆事件列表的鍵名。
     */
    public static final String CALENDAR_EVENT_LIST = "calendarEventList";
    /**
     * 用於存放公告列表的鍵名。
     */
    public static final String POST_LIST = "postList";
    /**
     * 用於存放在頁尾顯示的最新活動紀錄列表的鍵名。
     */
    public static final String FOOTER_RECORD_LIST = "footerRecordList";

    /**
     * 私有建構子，防止這個工具類別被實例化。
     */
    private ModelAttributeConstant() {
    }
}
