package com.web.ncumt.controller.advice;

import com.web.ncumt.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 全域的 Controller Advice，用於將通用資料添加到 Model 中。
 */
@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalControllerAdvice {

    /**
     * 從 HTTP Session 中獲取當前登入的使用者資訊，並將其添加到 Model 中。
     * <p>
     * 這個方法會在每個請求之前執行，將 session 中的 "currentUser" 屬性
     * (一個 {@link User} 實體) 添加到 Model 中。
     * 這樣，所有 Thymeleaf 模板都可以透過 ${currentUser} 訪問登入者的資料。
     *
     * @param session HTTP Session 物件。
     * @return 當前登入的 {@link User} 實體，如果未登入或 session 中不存在，則為 null。
     */
    @ModelAttribute("currentUser")
    public User addCurrentUserToModel(HttpSession session) {
        // 從 session 中獲取 "currentUser" 屬性
        return (User) session.getAttribute("currentUser");
    }

    /**
     * 判斷當前登入的使用者是否為管理員，並將結果添加到 Model 中。
     * <p>
     * 如果使用者已登入且其 role > 0，則 "isAdmin" 為 true。
     *
     * @param session HTTP Session 物件。
     * @return 如果是管理員則為 true，否則為 false。
     */
    @ModelAttribute("isAdmin")
    public boolean addIsAdminToModel(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null && currentUser.getRole() != null) {
            return currentUser.getRole() > 0;
        }
        return false;
    }
}
