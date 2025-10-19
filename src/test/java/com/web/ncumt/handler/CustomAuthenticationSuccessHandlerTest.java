package com.web.ncumt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ncumt.constant.MessageConstant;
import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.dto.LoginUser;
import com.web.ncumt.dto.NcuUser;
import com.web.ncumt.entity.User;
import com.web.ncumt.helper.ToastMessageHelper;
import com.web.ncumt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * {@link CustomAuthenticationSuccessHandler} 的單元測試。
 */
@ExtendWith(MockitoExtension.class)
class CustomAuthenticationSuccessHandlerTest {

    @Mock
    private RequestCache requestCache;

    @Mock
    private UserService userService;

    @Mock
    private ObjectMapper objectMapper;

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

    @Mock
    private OAuth2User oauth2User;

    @InjectMocks
    private CustomAuthenticationSuccessHandler successHandler;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setNameZh("測試使用者");
        user.setRole(0);

        NcuUser ncuUser = new NcuUser();

        when(request.getSession()).thenReturn(session);
        when(authentication.getPrincipal()).thenReturn(oauth2User);
        when(oauth2User.getAttributes()).thenReturn(Map.of("name", "value"));
        when(objectMapper.convertValue(any(Map.class), eq(NcuUser.class))).thenReturn(ncuUser);
        when(userService.findOrCreateUser(ncuUser)).thenReturn(user);
    }

    /**
     * 測試當存在已儲存的請求時，是否會重新導向到該請求的 URL。
     *
     * @throws IOException 如果發生 I/O 錯誤
     */
    @Test
    void onAuthenticationSuccess_withSavedRequest_shouldRedirectToSavedUrl() throws IOException {
        final String SAVED_URL = "/saved-url";
        // 編排
        SavedRequest savedRequest = mock(SavedRequest.class);
        when(savedRequest.getRedirectUrl()).thenReturn(SAVED_URL);
        when(requestCache.getRequest(request, response)).thenReturn(savedRequest);

        // 行動
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // 斷言
        verify(session).setAttribute(eq(SessionConstant.CURRENT_LOGIN_USER), any(LoginUser.class));
        verify(toastMessageHelper).addSuccessMessage(session, MessageConstant.LOGIN_SUCCESS(user.getNameZh()));
        verify(requestCache).removeRequest(request, response);
        verify(response).sendRedirect(SAVED_URL);
    }

    /**
     * 測試當不存在已儲存的請求時，是否會重新導向到登入前的 referer URL。
     *
     * @throws IOException 如果發生 I/O 錯誤
     */
    @Test
    void onAuthenticationSuccess_withPreLoginRefererUrl_shouldRedirectToReferer() throws IOException {
        final String REFERER_URL = "/referer-url";
        // 編排
        when(requestCache.getRequest(request, response)).thenReturn(null);
        when(session.getAttribute(SessionConstant.PRE_LOGIN_REFERER_URL)).thenReturn(REFERER_URL);

        // 行動
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // 斷言
        verify(session).setAttribute(eq(SessionConstant.CURRENT_LOGIN_USER), any(LoginUser.class));
        verify(toastMessageHelper).addSuccessMessage(session, MessageConstant.LOGIN_SUCCESS(user.getNameZh()));
        verify(session).removeAttribute(SessionConstant.PRE_LOGIN_REFERER_URL);
        verify(response).sendRedirect(REFERER_URL);
    }

    /**
     * 測試在沒有任何已儲存請求或 referer 的情況下，是否會作為備用方案重新導向到 {@link URLConstant#HOME}。
     *
     * @throws IOException 如果發生 I/O 錯誤
     */
    @Test
    void onAuthenticationSuccess_withNoSavedRequestOrReferer_shouldRedirectToRoot() throws IOException {
        // 編排
        when(requestCache.getRequest(request, response)).thenReturn(null);
        when(session.getAttribute(SessionConstant.PRE_LOGIN_REFERER_URL)).thenReturn(null);

        // 行動
        successHandler.onAuthenticationSuccess(request, response, authentication);

        // 斷言
        verify(session).setAttribute(eq(SessionConstant.CURRENT_LOGIN_USER), any(LoginUser.class));
        verify(toastMessageHelper).addSuccessMessage(session, MessageConstant.LOGIN_SUCCESS(user.getNameZh()));
        verify(response).sendRedirect(URLConstant.HOME);
    }
}
