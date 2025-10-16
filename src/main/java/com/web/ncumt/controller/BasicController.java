package com.web.ncumt.controller;

import com.web.ncumt.dto.CalendarEvent;
import com.web.ncumt.entity.Record;
import com.web.ncumt.service.EventService;
import com.web.ncumt.service.PostService;
import com.web.ncumt.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("recordList", recordService.listLatestRecord(5));
        model.addAttribute("pageTitle", "首頁");
        List<CalendarEvent> calendarEventList = eventService.listIndexCalendarEvent();
        model.addAttribute("calendarEventList", calendarEventList);
        return "home";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(Model model) {
        model.addAttribute("pageTitle", "關於我們");
        return "information/aboutUs";
    }

    @ModelAttribute("footerRecordList")
    public List<Record> footerRecordList() {
        return recordService.listLatestRecord(5);
    }

    @ModelAttribute("categoryArray")
    public List<String> categoryArray() {
        return Arrays.asList("中級山", "高山", "溯溪");
    }
}
