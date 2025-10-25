package com.web.ncumt.handler;

import com.web.ncumt.client.LineClient;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.dto.line.flex.FlexMessage;
import com.web.ncumt.exception.UnauthorizedOperationException;
import com.web.ncumt.helper.FlexMessageHelper;
import com.web.ncumt.helper.KibanaLinkHelper;
import com.web.ncumt.helper.ToastMessageHelper;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全域例外處理器，用於攔截和處理整個應用程式中的特定例外。
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ToastMessageHelper toastMessageHelper;

    @Autowired
    private LineClient lineClient;

    @Autowired
    private KibanaLinkHelper kibanaLinkHelper;

    @Autowired
    private FlexMessageHelper flexMessageHelper;

    /**
     * 處理權限不足的例外 (UnauthorizedOperationException)。
     */
    @ExceptionHandler(UnauthorizedOperationException.class)
    public String handleUnauthorizedOperation(UnauthorizedOperationException ex, HttpSession session) {
        toastMessageHelper.addErrorMessage(session, ex.getMessage());
        return URLConstant.redirectTo(URLConstant.LOGIN_OPTION);
    }

    /**
     * 處理所有未被特定處理器捕捉的通用例外。
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleGenericException(Exception ex) {
        log.error("An unexpected error occurred", ex);

        String requestId = MDC.get("request_id");
        String kibanaLink = null;
        if (requestId != null) {
            kibanaLink = kibanaLinkHelper.generateKibanaLink(requestId);
        } else {
            log.warn("request_id not found in MDC. Cannot generate Kibana link.");
        }

        FlexMessage flexMessage = flexMessageHelper.createExceptionAlert(
                "🚨 Unhandled Exception Alert 🚨",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                requestId,
                kibanaLink
        );

        lineClient.sendMessage(flexMessage);
    }

    //TODO log.error 發送警告訊息到 line
}
