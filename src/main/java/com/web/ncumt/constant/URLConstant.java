package com.web.ncumt.constant;

/**
 * 提供 URL 中儲存的 key 常量。
 */
public final class URLConstant {
    /**
     * 首頁的 URL。
     */
    public static final String HOME = "/";

    /**
     * 關於我們頁面的 URL。
     */
    public static final String ABOUT_US = "/aboutUs";

    /**
     * 登入選項頁面的 URL。
     */
    public static final String LOGIN_OPTION = "/portal/option";

    /**
     * 觸發登出的 URL。
     */
    public static final String LOGOUT_URL = "/logout";

    /**
     * 私有建構子，防止實例化。
     */
    private URLConstant() {
        // restrict instantiation
    }

}
