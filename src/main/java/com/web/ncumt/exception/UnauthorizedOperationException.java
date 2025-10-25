package com.web.ncumt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 當操作因權限不足而無法執行時拋出此例外。
 * <p>
 * 附加的 @ResponseStatus 會讓 Spring MVC 自動將此例外轉換為 HTTP 403 Forbidden 回應。
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizedOperationException extends RuntimeException {
    public UnauthorizedOperationException() {
        super("權限不足，無法進行此操作，請先登入。");
    }
}
