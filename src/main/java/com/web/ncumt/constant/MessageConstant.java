package com.web.ncumt.constant;

public final class MessageConstant {

    public static final String LOGOUT_SUCCESS = "您已成功登出！";

    public static final String AUTH_REQUIRED = "此頁面需要登入才能存取，請先登入！";

    public static final String ACCESS_DENIED = "您沒有權限存取此頁面。";

    private MessageConstant() {
        // restrict instantiation
    }

    /**
     * {name} + 登入成功！
     */
    public static String LOGIN_SUCCESS(String name) {
        return name + " 登入成功！";
    }
}
