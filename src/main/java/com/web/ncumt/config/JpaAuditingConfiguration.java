package com.web.ncumt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.ncumt.dto.user.NcuUser;
import com.web.ncumt.entity.User;
import com.web.ncumt.exception.UnauthorizedOperationException;
import com.web.ncumt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

/**
 * JpaAuditingConfiguration 類別用於配置 Spring Data JPA 的審計功能。
 * 透過此配置，可以自動記錄實體的創建者 (CreatedBy) 和最後修改者 (LastModifiedBy)。
 */
@Configuration // 標示這是一個配置類別，Spring 會掃描並處理其中的 Bean 定義
@Profile("!test") // 此配置在 'test' profile 未啟用時生效
public class JpaAuditingConfiguration {

    // 自動注入 UserService，用於根據郵箱查找用戶
    @Autowired
    private UserService userService;

    // 自動注入 ObjectMapper，用於將 OAuth2User 的屬性轉換為自定義的 NcuUser 物件
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 定義一個 AuditorAware 的 Bean，用於提供當前操作的審計者（Auditor）資訊。
     * 在這裡，審計者是當前登入用戶的 ID。
     * <p>
     * 實現邏輯如下：
     * <ol>
     *     <li>從 Spring Security 的安全上下文中獲取當前的 {@link Authentication} 認證資訊。</li>
     *     <li>如果用戶未認證（例如，匿名訪問），則拋出 {@link UnauthorizedOperationException}。</li>
     *     <li>獲取認證主體 (Principal)，並將其轉換為 {@link OAuth2User}。</li>
     *     <li>使用 ObjectMapper 將 OAuth2User 的屬性轉換為自定義的 {@link NcuUser} DTO。</li>
     *     <li>透過 {@link UserService} 根據郵箱從資料庫中查找對應的 {@link User} 實體。</li>
     *     <li>如果找到用戶，則返回該用戶的 ID。如果找不到（表示正在創建新用戶），則返回 {@code -1L} 表示系統建立。</li>
     * </ol>
     *
     * @return AuditorAware<Long> 的實例，它會返回一個包含審計者 ID 的 Optional。
     * @throws UnauthorizedOperationException 如果當前沒有已認證的用戶，則拋出此例外。
     */
    @Bean
    public AuditorAware<Long> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new UnauthorizedOperationException();
            }

            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            NcuUser ncuUser = objectMapper.convertValue(oauth2User.getAttributes(), NcuUser.class);
            Optional<User> user = userService.findByEmail(ncuUser.getEmail());

            // 如果找到用戶，則返回其 ID；否則返回 -1L，表示此為新用戶，由系統建立
            return user.map(User::getId).or(() -> Optional.of(-1L));
        };
    }
}
