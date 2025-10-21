package com.web.ncumt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ncumt.constant.MessageConstant;
import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.dto.LoginUser;
import com.web.ncumt.dto.NcuUser;
import com.web.ncumt.entity.User;
import com.web.ncumt.enums.Role;
import com.web.ncumt.helper.ToastMessageHelper;
import com.web.ncumt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 自訂的 OAuth2 登入成功處理器。
 * <p>
 * 當使用者透過 OAuth2 成功登入後，這個處理器會被觸發。它的主要職責包括：
 * 1. 根據 OAuth2 提供的使用者資訊，在資料庫中查找或創建對應的本地使用者帳號。
 * 2. 將登入使用者的基本資訊儲存到 Session 中。
 * 3. 使用 {@link ToastMessageHelper} 設定一個一次性的登入成功訊息。
 * 4. 根據登入前的來源，將使用者重導向到他們原本想訪問的頁面，或是一個預設頁面。
 * </p>
 */
@Component
@Slf4j
@SuppressWarnings("unused")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 用於快取和還原登入前請求的物件。
     */
    @Autowired
    private RequestCache requestCache;

    /**
     * 用於操作使用者資料的服務。
     */
    @Autowired
    private UserService userService;

    /**
     * 用於在 Java 物件和 JSON 之間進行轉換的物件。
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 用於新增 Toast 提示訊息的輔助類別。
     */
    @Autowired
    private ToastMessageHelper toastMessageHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        NcuUser ncuUser = objectMapper.convertValue(oauth2User.getAttributes(), NcuUser.class);
        User user = userService.findOrCreateUser(ncuUser);

        HttpSession session = request.getSession();

        session.setAttribute(SessionConstant.CURRENT_LOGIN_USER, LoginUser.builder()
                .nameZh(user.getNameZh())
                .role(Role.fromValue(user.getRole()))
                .build());
        log.debug("User '{}' stored in session.", user.getNameZh());

        // 使用 ToastMessageHelper 設定一次性的成功訊息
        toastMessageHelper.addSuccessMessage(session, MessageConstant.LOGIN_SUCCESS(user.getNameZh()));

        // 1. 優先使用 Spring Security 儲存的原始請求
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            requestCache.removeRequest(request, response);
            String targetUrl = savedRequest.getRedirectUrl();
            log.debug("Redirecting to saved request URL: {}", targetUrl);
            response.sendRedirect(targetUrl);
            return;
        }

        // 2. 作為備用方案，檢查我們手動儲存的 referer
        String preLoginRefererUrl = (String) session.getAttribute(SessionConstant.PRE_LOGIN_REFERER_URL);
        if (StringUtils.hasText(preLoginRefererUrl)) {
            session.removeAttribute(SessionConstant.PRE_LOGIN_REFERER_URL);
            log.debug("Redirecting to pre-login referer URL: {}", preLoginRefererUrl);
            response.sendRedirect(preLoginRefererUrl);
            return;
        }

        // 3. 如果都沒有，則導向到首頁
        response.sendRedirect(URLConstant.HOME);
    }
}
