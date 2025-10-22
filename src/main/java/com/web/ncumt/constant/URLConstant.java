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
     * 創建公告的 URL (單數路徑，如果未使用可考慮移除)。
     */
    public static final String POST_CREATE = "/post/create";

    /**
     * 私有建構子，防止實例化。
     */
    private URLConstant() {
        // restrict instantiation
    }

    /**
     * 重新導向至指定 url
     *
     * @param url 要導向的 url
     */
    public static String redirectTo(String url) {
        return "redirect:" + url;
    }
}
