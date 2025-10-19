package com.web.ncumt.handler;

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
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link CustomAuthenticationEntryPoint} 的單元測試。
 */
@ExtendWith(MockitoExtension.class)
class CustomAuthenticationEntryPointTest {

    @Mock
    private ToastMessageHelper toastMessageHelper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * 測試 commence 方法是否會新增一則警告訊息 {@link MessageConstant#AUTH_REQUIRED} 並重新導向到 {@link URLConstant#LOGIN_OPTION}。
     *
     * @throws IOException 如果在重新導向期間發生 I/O 錯誤
     */
    @Test
    void commence_shouldAddToastMessageAndRedirectToLoginOption() throws IOException {
        // 編排
        when(request.getSession()).thenReturn(session);

        // 行動
        customAuthenticationEntryPoint.commence(request, response, authException);

        // 斷言
        verify(toastMessageHelper).addWarningMessage(session, MessageConstant.AUTH_REQUIRED);
        verify(response).sendRedirect(URLConstant.LOGIN_OPTION);
    }
}
