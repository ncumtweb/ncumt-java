package com.web.ncumt.config;

import com.web.ncumt.handler.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 設定應用程式的 Web 安全性規則。
 * <p>
 * 這個設定類別定義了 Spring Security 的核心行為，包括：
 * <ul>
 *     <li><b>路徑授權:</b>
 *         <ul>
 *             <li>保護特定路徑，如 {@code /user/**}，要求使用者必須登入才能存取。</li>
 *             <li>所有其他路徑都公開允許。</li>
 *         </ul>
 *     </li>
 *     <li><b>OAuth2 登入:</b>
 *         <ul>
 *             <li>啟用 OAuth2 登入功能。</li>
 *         </ul>
 *     </li>
 *     <li><b>自定義登出:</b>
 *         <ul>
 *             <li>設定登出 URL 為 {@code /logout}。</li>
 *             <li>使用 {@link CustomLogoutSuccessHandler} 處理登出成功後的重新導向邏輯。</li>
 *         </ul>
 *     </li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig {

    public static final String PORTAL_LOGIN_URL = "/portal/login";

    public static final String LOGOUT_URL = "/logout";

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 保護需要登入才能存取的路徑
                        .requestMatchers(PORTAL_LOGIN_URL).authenticated()
                        // 其他所有路徑都公開
                        .anyRequest().permitAll()
                )
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_URL)
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .permitAll()
                );

        return http.build();
    }
}
