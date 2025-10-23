package com.web.ncumt.constant;

/**
 * 提供 URL 中儲存的 key 常量。
 */
public final class URLConstant {

    /**
     * 前端錯誤日誌的 URL。
     */
    public static final String FRONTEND_LOG_ERROR = "/frontend/log/error";

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
     * 創建公告的 URL
     */
    public static final String POST_CREATE = "/post/create";

    /**
     * 編輯公告的 URL
     */
    public static final String POST_EDIT = "/post/edit/{id}";

    /**
     * 刪除公告的 URL
     */
    public static final String POST_DELETE = "/post/delete/{id}";

    /**
     * 公告列表的 URL
     */
    public static final String POST_LIST = "/post/list";

    /**
     * 需要認證才能存取的 URL 列表。
     * 將所有需要認證的 URL 集中管理，方便維護。
     */
    public static final String[] AUTHENTICATED_URL_ARRAY = {
            POST_CREATE,
            POST_EDIT,
            POST_DELETE
    };

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
