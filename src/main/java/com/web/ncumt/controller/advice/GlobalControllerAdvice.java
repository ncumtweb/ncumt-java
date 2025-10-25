package com.web.ncumt.controller.advice;

import com.web.ncumt.constant.ModelAttributeConstant;
import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.dto.RecordFront;
import com.web.ncumt.dto.user.LoginUser;
import com.web.ncumt.service.RecordService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalControllerAdvice {

    @Autowired
    private RecordService recordService;

    /**
     * 將使用者狀態（是否為管理員、是否為社員）添加到 Model 中，供所有視圖使用。
     *
     * @param session HTTP Session 物件。
     * @param model   Spring MVC 的 Model 物件。
     */
    @ModelAttribute
    public void addUserStatusToModel(HttpSession session, Model model) {
        LoginUser currentLoginUser = (LoginUser) session.getAttribute(SessionConstant.CURRENT_LOGIN_USER);

        boolean isAdmin = false;
        boolean isClubMember = false;

        if (currentLoginUser != null && currentLoginUser.getRole() != null) {
            model.addAttribute(ModelAttributeConstant.CURRENT_LOGIN_USER, currentLoginUser);
            isAdmin = currentLoginUser.getRole().isAdmin();
            isClubMember = currentLoginUser.getRole().isClubMember();
        }

        model.addAttribute(ModelAttributeConstant.IS_ADMIN, isAdmin);
        model.addAttribute(ModelAttributeConstant.IS_CLUB_MEMBER, isClubMember);
    }


    /**
     * 提供頁尾顯示的最新活動紀錄列表。
     *
     * @return 最新的 5 筆活動紀錄
     */
    @ModelAttribute(ModelAttributeConstant.FOOTER_RECORD_LIST)
    public List<RecordFront> footerRecordList() {
        List<RecordFront> recordList = recordService.listLatestRecord(5);
        return recordService.listLatestRecord(5);
    }
}
