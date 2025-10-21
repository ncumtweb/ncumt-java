package com.web.ncumt.controller;

import com.web.ncumt.constant.ModelAttributeConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.constant.ViewNameConstant;
import com.web.ncumt.dto.CalendarEvent;
import com.web.ncumt.service.EventService;
import com.web.ncumt.service.PostService;
import com.web.ncumt.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 用於操作活動紀錄的服務。
     */
    @Autowired
    private RecordService recordService;

    /**
     * 用於操作文章的服務。
     */
    @Autowired
    private PostService postService;

    /**
     * 用於操作事件的服務。
     */
    @Autowired
    private EventService eventService;

    /**
     * 處理首頁請求。
     *
     * @param model Spring MVC 的 Model 物件
     * @return home 視圖名稱
     */
    @GetMapping(URLConstant.HOME)
    public String home(Model model) {
        model.addAttribute(ModelAttributeConstant.RECORD_LIST, recordService.listLatestRecord(5));
        model.addAttribute(ModelAttributeConstant.POST_LIST, postService.listActivePost());
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "首頁");
        List<CalendarEvent> calendarEventList = eventService.listIndexCalendarEvent();
        model.addAttribute(ModelAttributeConstant.CALENDAR_EVENT_LIST, calendarEventList);
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
