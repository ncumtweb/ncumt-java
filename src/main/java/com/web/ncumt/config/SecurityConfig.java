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
     * 定義並建立安全過濾器鏈 (Security Filter Chain)。
     * <p>
     * 這個 Bean 是 Spring Security 的核心，它定義了如何處理所有傳入的 HTTP 請求。
     * </p>
     *
     * @param http HttpSecurity 物件，用於建立安全規則
     * @return 設定好的 SecurityFilterChain
     * @throws Exception 如果設定過程中發生錯誤
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/aboutUs").authenticated()
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
