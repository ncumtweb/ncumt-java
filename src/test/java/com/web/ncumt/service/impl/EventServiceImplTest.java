package com.web.ncumt.service.impl;

import com.web.ncumt.dto.CalendarEvent;
import com.web.ncumt.entity.Event;
import com.web.ncumt.enums.EventCategory;
import com.web.ncumt.repository.EventRepository;
import com.web.ncumt.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * {@link EventServiceImpl} 的整合測試，使用 H2 資料庫。
 */

@SuppressWarnings("unused")
class EventServiceImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
    }

    @Test
    @DisplayName("應能正確地將事件列表轉換為日曆事件列表")
    void listIndexCalendarEvent_shouldReturnListOfCalendarEvents() {
        // 編排
        // 解決方案 1: 手動將時間精度截斷至微秒，與資料庫的精度保持一致
        LocalDateTime courseStart = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        LocalDateTime courseEnd = LocalDateTime.now().plusHours(2).truncatedTo(ChronoUnit.MICROS);
        Event courseEvent = new Event();
        courseEvent.setTitle("社課");
        courseEvent.setStart(courseStart);
        courseEvent.setEnd(courseEnd);
        courseEvent.setCategory(EventCategory.COURSE.getCategoryIndex());

        LocalDateTime outingStart = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MICROS);
        LocalDateTime outingEnd = LocalDateTime.now().plusDays(1).plusHours(8).truncatedTo(ChronoUnit.MICROS);
        Event outingEvent = new Event();
        outingEvent.setTitle("出隊");
        outingEvent.setStart(outingStart);
        outingEvent.setEnd(outingEnd);
        outingEvent.setCategory(EventCategory.OUTING.getCategoryIndex());

        eventRepository.saveAll(List.of(courseEvent, outingEvent));

        // 行動
        List<CalendarEvent> calendarEvents = eventService.listIndexCalendarEvent();

        // 斷言
        assertThat(calendarEvents).hasSize(2);

        // 解決方案 2: 使用 extracting 和 containsExactlyInAnyOrder 進行不依賴順序的健壯斷言
        // 這種方法可以一次性驗證所有物件的屬性，且不在乎它們在列表中的順序
        assertThat(calendarEvents).extracting(
                CalendarEvent::getTitle,
                CalendarEvent::getStart,
                CalendarEvent::getEnd,
                CalendarEvent::getColor
        ).containsExactlyInAnyOrder(
                tuple(
                        EventCategory.COURSE.getPrefix() + courseEvent.getTitle(),
                        courseStart,
                        courseEnd,
                        EventCategory.COURSE.getColor()
                ),
                tuple(
                        EventCategory.OUTING.getPrefix() + outingEvent.getTitle(),
                        outingStart,
                        outingEnd,
                        EventCategory.OUTING.getColor()
                )
        );
    }

    @Test
    @DisplayName("當資料庫沒有任何事件時，應回傳空列表")
    void listIndexCalendarEvent_whenNoEvents_shouldReturnEmptyList() {
        // 編排 - 資料庫已由 @BeforeEach 清空

        // 行動
        List<CalendarEvent> calendarEvents = eventService.listIndexCalendarEvent();

        // 斷言
        assertThat(calendarEvents).isEmpty();
    }
}
