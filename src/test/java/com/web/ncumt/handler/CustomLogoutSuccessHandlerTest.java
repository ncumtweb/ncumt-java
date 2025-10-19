package com.web.ncumt.handler;

import com.web.ncumt.constant.HeaderConstant;
import com.web.ncumt.constant.MessageConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.helper.ToastMessageHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link CustomLogoutSuccessHandler} 的單元測試。
 */
@ExtendWith(MockitoExtension.class)
class CustomLogoutSuccessHandlerTest {

    @Mock
    private ToastMessageHelper toastMessageHelper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    /**
     * 測試 onLogoutSuccess 方法是否會新增一則成功訊息並重新導向到 referer URL。
     *
     * @throws IOException 如果在重新導向期間發生 I/O 錯誤
     */
    @Test
    void onLogoutSuccess_withReferer_shouldRedirectToReferer() throws IOException {
        // 編排
        String refererUrl = "/previous-page";
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(HeaderConstant.REFERER)).thenReturn(refererUrl);

        // 行動
        customLogoutSuccessHandler.onLogoutSuccess(request, response, authentication);

        // 斷言
        verify(toastMessageHelper).addSuccessMessage(session, MessageConstant.LOGOUT_SUCCESS);
        verify(response).sendRedirect(refererUrl);
    }

    /**
     * 測試當沒有 referer 時，onLogoutSuccess 方法是否會新增一則成功訊息並重新導向到 {@link URLConstant#HOME}。
     *
     * @throws IOException 如果在重新導向期間發生 I/O 錯誤
     */
    @Test
    void onLogoutSuccess_withoutReferer_shouldRedirectToRoot() throws IOException {
        // 編排
        when(request.getSession()).thenReturn(session);
        when(request.getHeader(HeaderConstant.REFERER)).thenReturn(null);

        // 行動
        customLogoutSuccessHandler.onLogoutSuccess(request, response, authentication);

        // 斷言
        verify(toastMessageHelper).addSuccessMessage(session, MessageConstant.LOGOUT_SUCCESS);
        verify(response).sendRedirect(URLConstant.HOME);
    }
}
