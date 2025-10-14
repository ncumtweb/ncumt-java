package com.web.ncumt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.controller.data.LoginUserDTO;
import com.web.ncumt.controller.data.NcuUserDTO;
import com.web.ncumt.entity.User;
import com.web.ncumt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
@Slf4j
@SuppressWarnings("unused")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        log.debug("OAuth2 User Attributes: {}", oauth2User.getAttributes());

        NcuUserDTO ncuUserDTO = objectMapper.convertValue(oauth2User.getAttributes(), NcuUserDTO.class);
        User user = userService.findOrCreateUser(ncuUserDTO);

        HttpSession session = request.getSession();

        session.setAttribute(SessionConstant.CURRENT_LOGIN_USER, LoginUserDTO.builder()
                .nameZh(user.getNameZh())
                .isLoginSuccess(true)
                .role(user.getRole())
                .build());

        // 設定一次性的成功訊息標記
        log.debug("User '{}' stored in session.", user.getNameZh());

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
        String preLoginRefererUrl = (String) session.getAttribute("preLoginRefererUrl");
        if (StringUtils.hasText(preLoginRefererUrl)) {
            session.removeAttribute("preLoginRefererUrl");
            log.debug("Redirecting to pre-login referer URL: {}", preLoginRefererUrl);
            response.sendRedirect(preLoginRefererUrl);
            return;
        }

        // 3. 如果都沒有，則導向到首頁
        response.sendRedirect("/");
    }
}
