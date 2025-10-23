package com.web.ncumt.controller;

import com.web.ncumt.constant.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * PortalController 處理與使用者入口和登入流程相關的請求。
 */
@Controller
@Slf4j
@SuppressWarnings("unused")
public class PortalController {

    /**
     * 顯示登入選項頁面。
     * <p>
     * 在使用者訪問登入頁面之前，這個方法會儲存他們先前的 URL (Referer)，
     * 以便在登入成功後將他們重新導向到原來的位置。
     * </p>
     *
     * @param model   Spring MVC 的 Model 物件
     * @param request HttpServletRequest 物件，用於取得 Referer 和 Session
     * @return portal/option 視圖名稱
     */
    @GetMapping(URLConstant.LOGIN_OPTION)
    public String portalOption(Model model, HttpServletRequest request) {
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "登入選項");
        String referer = request.getHeader(HeaderConstant.REFERER);
        if (referer != null && !referer.contains(URLConstant.LOGIN_OPTION)) {
            request.getSession().setAttribute(SessionConstant.PRE_LOGIN_REFERER_URL, referer);
        }
        return ViewNameConstant.LOGIN_OPTION;
    }
}
