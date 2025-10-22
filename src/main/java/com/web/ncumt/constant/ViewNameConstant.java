package com.web.ncumt.constant;

/**
 * 這個類別存放了在 Spring MVC 中使用的視圖名稱（View Names）。
 * 使用這個類別可以集中管理視圖路徑，避免在控制器中直接使用字串，
 * 從而提高程式碼的可讀性和可維護性。
 */
public final class ViewNameConstant {

    /**
     * 首頁的視圖名稱。
     */
    public static final String HOME = "home";
    /**
     * 關於我們的視圖名稱。
     */
    public static final String ABOUT_US = "information/aboutUs";
    /**
     * 登入選項的視圖名稱。
     */
    public static final String LOGIN_OPTION = "portal/option";

    /**
     * 創建公告的視圖名稱。
     */
    public static final String CREATE_POST = "post/create";

    /**
     * 私有建構子，防止這個工具類別被實例化。
     */
    private ViewNameConstant() {
    }
}
