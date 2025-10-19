package com.web.ncumt.handler;

import com.web.ncumt.constant.HeaderConstant;
import com.web.ncumt.constant.MessageConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.helper.ToastMessageHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自訂的登出成功處理器。
 * <p>
 * 核心功能:
 * <ul>
 *     <li>在使用者成功登出後，使用 {@link ToastMessageHelper} 將一則一次性的登出成功訊息存入 Session。</li>
 *     <li>決定要將使用者重新導向到哪個頁面。它會檢查使用者登出前所在的頁面 (透過 HTTP Referer 標頭)。</li>
 *     <li>如果 Referer 標頭存在，則將使用者導向回他們登出前所在的頁面。</li>
 *     <li>否則，將使用者導向到首頁 (/)。</li>
 * </ul>
 */
@Component
@SuppressWarnings("unused")
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 用於新增 Toast 提示訊息的輔助類別。
     */
    @Autowired
    private ToastMessageHelper toastMessageHelper;

    /**
     * 當使用者成功登出時，此方法會被呼叫。
     * <p>
     * 它的主要功能是設定一則登出成功的快閃訊息，並將使用者重新導向回他們登出前所在的頁面。
     * 它會透過 HTTP 的 "Referer" 標頭來獲取使用者先前的頁面 URL。
     *
     * @param request        HTTP 請求物件
     * @param response       HTTP 回應物件
     * @param authentication 包含使用者認證資訊的物件 (在登出後可能為 null)
     * @throws IOException 如果重新導向時發生 I/O 錯誤
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        HttpSession session = request.getSession();

        toastMessageHelper.addSuccessMessage(session, MessageConstant.LOGOUT_SUCCESS);

        // 決定重導向的 URL
        String refererUrl = request.getHeader(HeaderConstant.REFERER);
        String targetUrl = (refererUrl != null) ? refererUrl : URLConstant.HOME;

        response.sendRedirect(targetUrl);
    }
}
