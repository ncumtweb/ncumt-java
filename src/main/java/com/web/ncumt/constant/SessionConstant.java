package com.web.ncumt.constant;

/**
 * 提供 session 中儲存的 key 常量。
 */
public final class SessionConstant {

    /**
     * 儲存當前登入使用者的 session key。
     */
    public static final String CURRENT_LOGIN_USER = "currentLoginUser";
    /**
     * 儲存 Toast 提示訊息的 session key。
     */
    public static final String TOAST_MESSAGE = "toastMessage";

    public static final String PRE_LOGIN_REFERER_URL = "preLoginRefererUrl";

    /**
     * 私有建構子，防止實例化。
     */
    private SessionConstant() {
        // restrict instantiation
    }
}
