package com.web.ncumt.interceptor;

import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.dto.ToastMessage;
import com.web.ncumt.helper.ToastMessageHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 一次性 Toast 提示訊息攔截器。
 * <p>
 * 這個攔截器的主要功能是在 Controller 處理完請求後、頁面渲染前，
 * 透過 {@link ToastMessageHelper} 從 Session 中讀取並清除 Toast 訊息，
 * 然後將該訊息放入 ModelAndView 中，供前端視圖使用。
 * </p>
 */
@Component
@SuppressWarnings("unused")
public class ToastMessageInterceptor implements HandlerInterceptor {

    @Autowired
    private ToastMessageHelper toastMessageHelper;

    /**
     * 在 Controller 方法執行後，視圖被渲染前執行。
     *
     * @param request      當前請求
     * @param response     當前回應
     * @param handler      選擇的處理器
     * @param modelAndView Controller 返回的 ModelAndView
     */
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable ModelAndView modelAndView) {
        if (modelAndView != null && !isRedirectView(modelAndView)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                ToastMessage toastMessage = toastMessageHelper.getAndClearMessage(session);
                if (toastMessage != null) {
                    modelAndView.addObject(SessionConstant.TOAST_MESSAGE, toastMessage);
                }
            }
        }
    }

    /**
     * 檢查 ModelAndView 是否代表一個重定向視圖。
     *
     * @param modelAndView 欲檢查的 ModelAndView
     * @return 如果是重定向視圖則返回 true，否則返回 false
     */
    private boolean isRedirectView(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();
        return viewName != null && viewName.startsWith("redirect:");
    }
}
