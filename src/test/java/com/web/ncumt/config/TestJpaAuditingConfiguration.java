package com.web.ncumt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 測試環境專用的 JPA 審計配置。
 * <p>
 * 當 'test' profile 被啟用時，此配置會生效，並提供一個簡化的 AuditorAware Bean，
 * 它會始終返回一個固定的 ID (-1L)，從而繞過對 Spring Security 上下文的依賴。
 */
@Configuration
@Profile("test")
public class TestJpaAuditingConfiguration {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        // 在測試中，我們不需要一個真正的用戶，直接返回一個代表「系統」或「測試用戶」的固定 ID。
        return () -> Optional.of(-1L);
    }
}
