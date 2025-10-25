package com.web.ncumt.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

/**
 * 自定義的整合測試註解，組合了 {@link SpringBootTest} 和 {@link ActiveProfiles}。
 * <p>
 * 使用此註解可以確保所有整合測試都自動啟用 'test' profile，
 * 從而簡化測試類別的配置並保持一致性。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings("unused")
public @interface IntegrationTest {

    /**
     * {@link SpringBootTest#webEnvironment} 的別名。
     * 允許在需要時自定義 web 環境，預設為 MOCK。
     */
    @AliasFor(annotation = SpringBootTest.class, attribute = "webEnvironment")
    SpringBootTest.WebEnvironment webEnvironment() default SpringBootTest.WebEnvironment.MOCK;

    /**
     * {@link SpringBootTest#classes} 的別名。
     * 允許在需要時指定要載入的特定配置類別。
     */
    @AliasFor(annotation = SpringBootTest.class, attribute = "classes")
    Class<?>[] classes() default {};
}
