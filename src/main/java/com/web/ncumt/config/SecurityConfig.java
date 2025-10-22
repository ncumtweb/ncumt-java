package com.web.ncumt.config;

import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.handler.CustomAuthenticationEntryPoint;
import com.web.ncumt.handler.CustomAuthenticationSuccessHandler;
import com.web.ncumt.handler.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

/**
 * Spring Security 的主要組態設定。
 * <p>
 * 這個類別負責定義應用程式的所有安全相關規則，包括：
 * <ul>
 *     <li>設定哪些 URL 路徑需要認證，哪些可以公開存取。</li>
 *     <li>設定 OAuth2 登入流程，並指定自訂的成功處理器。</li>
 *     <li>設定登出流程，並指定自訂的成功處理器。</li>
 *     <li>設定自訂的認證入口點，用於處理未登入使用者存取受保護資源的情況。</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig {

    /**
     * 自訂的登出成功處理器。
     */
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    /**
     * 自訂的認證成功處理器。
     */
    @Autowired
    @Lazy
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /**
     * 自訂的認證入口點。
     */
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * 設定並建立安全過濾器鏈 (Security Filter Chain)，此鏈定義了應用程式的 HTTP 安全性規則。
     *
     * <p>詳細設定如下：</p>
     * <ul>
     *   <li><b>CSRF 保護：</b> 針對 {@link URLConstant#FRONTEND_LOG_ERROR} 路徑停用 CSRF 保護，以允許前端日誌記錄。</li>
     *   <li><b>請求授權：</b>
     *     <ul>
     *       <li>需要認證的 URL (如新增文章等) 定義在 {@link URLConstant#AUTHENTICATED_URL_ARRAY} 中。</li>
     *       <li>所有其他請求都允許公開存取。</li>
     *     </ul>
     *   </li>
     *   <li><b>OAuth2 登入：</b>
     *     <ul>
     *       <li>自訂登入頁面路徑為 {@link URLConstant#LOGIN_OPTION}。</li>
     *       <li>登入成功後由 {@link CustomAuthenticationSuccessHandler} 處理。</li>
     *     </ul>
     *   </li>
     *   <li><b>登出：</b>
     *     <ul>
     *       <li>登出 URL 為 {@link URLConstant#LOGOUT_URL}。</li>
     *       <li>登出成功後由 {@link CustomLogoutSuccessHandler} 處理。</li>
     *     </ul>
     *   </li>
     *   <li><b>例外處理：</b>
     *     <ul>
     *       <li>未經認證的存取請求將由 {@link CustomAuthenticationEntryPoint} 處理，引導使用者進行登入。</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @param http HttpSecurity 物件，用於 fluent API 風格的安全性設定。
     * @return 設定完成的 {@link SecurityFilterChain} 實例。
     * @throws Exception 如果設定過程中發生錯誤。
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(URLConstant.FRONTEND_LOG_ERROR)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(URLConstant.AUTHENTICATED_URL_ARRAY).authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage(URLConstant.LOGIN_OPTION)
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl(URLConstant.LOGOUT_URL)
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                );

        return http.build();
    }

    /**
     * 建立一個 RequestCache Bean，用於儲存和還原登入前的請求。
     *
     * @return 一個 HttpSessionRequestCache 實例
     */
    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }
}
