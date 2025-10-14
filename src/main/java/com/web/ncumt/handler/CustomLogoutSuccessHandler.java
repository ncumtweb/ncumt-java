package com.web.ncumt.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定義的登出成功處理器。
 * <p>
 * 核心功能:
 * <ul>
 *     <li>在使用者成功登出後，決定要將使用者重新導向到哪個頁面。</li>
 *     <li>它會檢查使用者登出前所在的頁面 (透過 HTTP Referer 標頭)。</li>
 *     <li>如果 Referer 頁面是需要登入才能存取的頁面 (例如 /user/**)，則會將使用者導向到首頁 (/)。</li>
 *     <li>否則，將使用者導向回他們登出前所在的頁面。</li>
 * </ul>
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 當使用者成功登出時，此方法會被呼叫。
     *
     * <p>
     * 它的主要功能是將使用者重新導向回他們登出前所在的頁面，並附加一個 `logout=true` 查詢參數。
     * 它會透過 HTTP 的 "Referer" 標頭來獲取使用者先前的頁面 URL。
     * 如果 "Referer" 標頭存在，使用者將被導向回該 URL。
     * 否則，使用者將被導向到預設的首頁 ("/")。
     *
     * @param request        HTTP 請求物件
     * @param response       HTTP 回應物件
     * @param authentication 包含使用者認證資訊的物件
     * @throws IOException 如果重新導向時發生 I/O 錯誤
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String refererUrl = request.getHeader("Referer");

        String targetUrl = "/";

        if (refererUrl != null) {
            targetUrl = refererUrl;
        }

        String finalUrl;
        if (targetUrl.contains("?")) {
            finalUrl = targetUrl + "&logout=true";
        } else {
            finalUrl = targetUrl + "?logout=true";
        }

        response.sendRedirect(finalUrl);
    }
}
