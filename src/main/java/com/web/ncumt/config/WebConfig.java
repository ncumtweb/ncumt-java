package com.web.ncumt.config;

import com.web.ncumt.interceptor.ToastMessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Web MVC 的主要組態設定。
 * <p>
 * 這個類別用於自訂 Spring MVC 的行為，例如註冊攔截器 (Interceptor)。
 * </p>
 */
@Configuration
@SuppressWarnings("unused")
public class WebConfig implements WebMvcConfigurer {

    /**
     * 用於處理 Toast 提示訊息的攔截器。
     */
    @Autowired
    private ToastMessageInterceptor toastMessageInterceptor;

    /**
     * 註冊應用程式的攔截器。
     * <p>
     * 這裡我們註冊了 {@link ToastMessageInterceptor}，使其能夠在所有請求中處理一次性 Toast 提示訊息。
     * </p>
     *
     * @param registry 攔截器註冊表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(toastMessageInterceptor);
    }
}
