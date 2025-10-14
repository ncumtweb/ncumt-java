package com.web.ncumt.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.web.ncumt.config.SecurityConfig.LOGIN_OPTION;

/**
 * PortalController 處理與使用者入口和登入流程相關的請求。
 */
@Controller
@Slf4j
@SuppressWarnings("unused")
public class PortalController {

    @GetMapping("/portal/option")
    public String portalOption(Model model, HttpServletRequest request) {
        model.addAttribute("pageTitle", "登入選項");
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains(LOGIN_OPTION)) {
            request.getSession().setAttribute("preLoginRefererUrl", referer);
        }
        return "portal/option";
    }
}
