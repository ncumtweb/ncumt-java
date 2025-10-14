package com.web.ncumt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ncumt.config.SecurityConfig;
import com.web.ncumt.controller.data.NcuUserDTO;
import com.web.ncumt.entity.User;
import com.web.ncumt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PortalController 處理與使用者入口和登入流程相關的請求。
 * <p>
 * 核心功能:
 * <ul>
 *     <li>提供一個 {@code /portal/login} 端點，作為 OAuth2 登入成功後的中繼站。</li>
 *     <li>在使用者登入成功後，此 Controller 會被呼叫，並儲存或更新使用者資訊，然後將使用者實體存入 Session。</li>
 *     <li>支援一個名為 'continue' 的 URL 參數。如果此參數存在且為一個安全的本地路徑，
 *         使用者將在登入後被重新導向到該路徑。</li>
 *     <li>如果 'continue' 參數不存在或不安全，使用者將被預設導向到網站首頁 ('/')。</li>
 * </ul>
 * <p>
 * 這個設計模式將登入成功後的處理邏輯（例如，記錄日誌、更新使用者資料）與最終的重新導向解耦，
 * 使得程式碼更加清晰和易於維護。
 */
@Controller
@Slf4j
@SuppressWarnings("unused")
public class PortalController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/portal/option")
    public String portalOption(Model model, HttpServletRequest request) {
        model.addAttribute("pageTitle", "登入選項");
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/portal/option") && !referer.contains("/portal/login")) {
            request.getSession().setAttribute("preLoginRefererUrl", referer);
        }
        return "portal/option";
    }

    /**
     * 處理 OAuth2 登入成功後的請求，並執行後續的重新導向。
     * <p>
     * 此方法會先將 OAuth2 使用者屬性轉換為 {@link NcuUserDTO}，
     * 然後呼叫 {@link UserService} 在資料庫中尋找或建立使用者，
     * 並將返回的 {@link User} 實體存入 HTTP Session 中。
     * 最後再根據 'continue' 參數決定重新導向的目的地。
     *
     * @param oauth2User  Spring Security 自動注入的已登入使用者物件。
     * @param continueUrl 來自 URL 的可選 'continue' 參數，用於指定登入後的目標路徑。
     * @param request     HTTP 請求物件，用於獲取 session。
     * @return 一個重新導向指令 (e.g., "redirect:/some/path" or "redirect:/")。
     */
    @GetMapping(SecurityConfig.PORTAL_LOGIN_URL)
    public String login(@AuthenticationPrincipal OAuth2User oauth2User,
                        @RequestParam(name = "continue", required = false) String continueUrl,
                        HttpServletRequest request) {
        log.debug("OAuth2 User Attributes: {}", oauth2User.getAttributes());

        NcuUserDTO ncuUserDTO = objectMapper.convertValue(oauth2User.getAttributes(), NcuUserDTO.class);

        // 根據 DTO 的內容，尋找或建立本地使用者紀錄
        User user = userService.findOrCreateUser(ncuUserDTO);

        // 將使用者實體儲存到 session 中，以便後續請求使用
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);
        log.debug("User '{}' stored in session.", user.getNameZh());

        // 安全性檢查：確保 continueUrl 是一個本地路徑，防止 Open Redirect 攻擊
        if (StringUtils.hasText(continueUrl) && continueUrl.startsWith("/")) {
            log.debug("Redirecting to continue URL: {}", continueUrl);
            return "redirect:" + continueUrl;
        }

        String preLoginRefererUrl = (String) session.getAttribute("preLoginRefererUrl");
        if (StringUtils.hasText(preLoginRefererUrl)) {
            session.removeAttribute("preLoginRefererUrl");
            log.debug("Redirecting to pre-login referer URL: {}", preLoginRefererUrl);
            return "redirect:" + preLoginRefererUrl;
        }

        // 如果沒有 continueUrl 或格式不符，則預設導向到首頁
        return "redirect:/";
    }
}
