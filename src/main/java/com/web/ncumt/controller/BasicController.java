package com.web.ncumt.controller;

import com.web.ncumt.constant.ModelAttributeConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.constant.ViewNameConstant;
import com.web.ncumt.dto.CalendarEvent;
import com.web.ncumt.dto.PostFront;
import com.web.ncumt.service.EventService;
import com.web.ncumt.service.PostService;
import com.web.ncumt.service.RecordService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 處理基本頁面請求的控制器，例如首頁和「關於我們」。
 */
@Controller
@SuppressWarnings("unused")
@Slf4j
public class BasicController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private PostService postService;

    @Autowired
    private EventService eventService;

    /**
     * 處理首頁請求。
     *
     * @param model Spring MVC 的 Model 物件
     * @param session HTTP Session 物件
     * @param pageable 分頁資訊
     * @return home 視圖名稱
     */
    @GetMapping(URLConstant.HOME)
    public String home(Model model, HttpSession session,
                       @PageableDefault(sort = {"pin", "createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        // 原有邏輯
        model.addAttribute(ModelAttributeConstant.RECORD_LIST, recordService.listLatestRecord(5));
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "首頁");
        List<CalendarEvent> calendarEventList = eventService.listIndexCalendarEvent();
        model.addAttribute(ModelAttributeConstant.CALENDAR_EVENT_LIST, calendarEventList);

        // 公告分頁邏輯 (已改為只查詢有效公告)
        Page<PostFront> postPage = postService.pageActivePost(pageable);
        model.addAttribute(ModelAttributeConstant.POST_PAGE, postPage);

        return ViewNameConstant.HOME;
    }

    /**
     * 處理「關於我們」頁面請求。
     *
     * @param model Spring MVC 的 Model 物件
     * @return information/aboutUs 視圖名稱
     */
    @GetMapping(URLConstant.ABOUT_US)
    public String aboutUs(Model model) {
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "關於我們");
        return ViewNameConstant.ABOUT_US;
    }
}
