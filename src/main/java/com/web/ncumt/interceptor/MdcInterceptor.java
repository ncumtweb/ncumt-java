package com.web.ncumt.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

/**
 * MDC 攔截器，用於在請求處理的生命週期中管理日誌上下文。
 * <p>
 * 主要職責是在每個請求開始時，產生一個唯一的 request_id 並放入 MDC，
 * 以便追蹤整個請求鏈中的所有日誌。請求結束後，會清除 MDC 以防記憶體洩漏。
 */
@Component
public class MdcInterceptor implements HandlerInterceptor {

    private static final String REQUEST_ID_HEADER = "request_id";

    @Override
    public boolean preHandle(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        String requestId = UUID.randomUUID().toString();
        MDC.put(REQUEST_ID_HEADER, requestId);
        return true;
    }

    @Override
    public void afterCompletion(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler, Exception ex) {
        MDC.remove(REQUEST_ID_HEADER);
    }
}
