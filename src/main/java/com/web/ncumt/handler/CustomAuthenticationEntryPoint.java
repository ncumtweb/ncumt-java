package com.web.ncumt.handler;

import com.web.ncumt.config.SecurityConfig;
import com.web.ncumt.helper.ToastMessageHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自訂的認證入口點 (Authentication Entry Point)。
 * <p>
 * 當一個未經認證的使用者嘗試存取需要權限的資源時，這個類別會被觸發。
 * 它的主要職責是：
 * 1. 設定一則提示使用者需要登入的 Toast 訊息。
 * 2. 將使用者重導向到登入頁面。
 * </p>
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @SuppressWarnings("unused")
    private ToastMessageHelper toastMessageHelper;

    /**
     * 開始認證流程。
     *
     * @param request       請求物件
     * @param response      回應物件
     * @param authException 觸發此方法的認證例外
     * @throws IOException 如果重導向時發生 I/O 錯誤
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        HttpSession session = request.getSession();

        // 使用 ToastMessageHelper 設定一次性的警告訊息
        toastMessageHelper.addWarningMessage(session, "此頁面需要登入才能存取，請先登入。");

        // 將使用者重導向到登入選項頁面
        response.sendRedirect(SecurityConfig.LOGIN_OPTION);
    }
}
