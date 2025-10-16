package com.web.ncumt.helper;

import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.dto.ToastMessage;
import com.web.ncumt.enums.ToastMessageType;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

/**
 * 用於管理一次性 Toast 提示訊息的輔助元件。
 * <p>
 * 這個元件是管理 Toast 訊息 Session 存取的唯一權威，封裝了所有讀寫操作的底層邏輯。
 * 它不直接依賴 Web 上下文，而是要求呼叫者傳入 {@link HttpSession}，這使其易於在任何環境中進行單元測試。
 * </p>
 */
@Component
public class ToastMessageHelper {

    /**
     * 新增一則「成功」類型的 Toast 訊息。
     *
     * @param session 當前的 HttpSession
     * @param message 訊息內容
     */
    public void addSuccessMessage(HttpSession session, String message) {
        addMessage(session, ToastMessageType.SUCCESS, message);
    }

    /**
     * 新增一則「錯誤」類型的 Toast 訊息。
     *
     * @param session 當前的 HttpSession
     * @param message 訊息內容
     */
    public void addErrorMessage(HttpSession session, String message) {
        addMessage(session, ToastMessageType.ERROR, message);
    }

    /**
     * 新增一則「警告」類型的 Toast 訊息。
     *
     * @param session 當前的 HttpSession
     * @param message 訊息內容
     */
    public void addWarningMessage(HttpSession session, String message) {
        addMessage(session, ToastMessageType.WARNING, message);
    }

    /**
     * 新增一則「資訊」類型的 Toast 訊息。
     *
     * @param session 當前的 HttpSession
     * @param message 訊息內容
     */
    public void addInfoMessage(HttpSession session, String message) {
        addMessage(session, ToastMessageType.INFO, message);
    }

    /**
     * 從 Session 中讀取 Toast 訊息，並在讀取後立即將其從 Session 中清除。
     *
     * @param session 當前的 HttpSession
     * @return 一個 {@link ToastMessage} 物件，如果沒有訊息則返回 null
     */
    public ToastMessage getAndClearMessage(HttpSession session) {
        ToastMessage toastMessage = (ToastMessage) session.getAttribute(SessionConstant.TOAST_MESSAGE);
        if (toastMessage != null) {
            session.removeAttribute(SessionConstant.TOAST_MESSAGE);
        }
        return toastMessage;
    }

    /**
     * 將指定類型和內容的訊息存入 Session。
     * 如果 Session 中已存在一則訊息，它將被覆蓋。
     *
     * @param session 當前的 HttpSession
     * @param type    訊息類型 (e.g., SUCCESS, ERROR)
     * @param message 訊息內容
     */
    private void addMessage(HttpSession session, ToastMessageType type, String message) {
        session.setAttribute(SessionConstant.TOAST_MESSAGE, new ToastMessage(type, message));
    }
}
