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
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link EventServiceImpl} 的整合測試，使用 H2 資料庫。
 */
@SpringBootTest
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
        Event courseEvent = new Event();
        courseEvent.setTitle("社課");
        courseEvent.setStart(LocalDateTime.now());
        courseEvent.setEnd(LocalDateTime.now().plusHours(2));
        courseEvent.setCategory(EventCategory.COURSE.getCategoryIndex());

        Event outingEvent = new Event();
        outingEvent.setTitle("出隊");
        outingEvent.setStart(LocalDateTime.now().plusDays(1));
        outingEvent.setEnd(LocalDateTime.now().plusDays(1).plusHours(8));
        outingEvent.setCategory(EventCategory.OUTING.getCategoryIndex());

        eventRepository.saveAll(List.of(courseEvent, outingEvent));

        // 行動
        List<CalendarEvent> calendarEvents = eventService.listIndexCalendarEvent();

        // 斷言
        assertThat(calendarEvents).hasSize(2);

        // 驗證社課事件
        assertThat(calendarEvents)
                .anySatisfy(event -> {
                    assertThat(event.getTitle()).isEqualTo(EventCategory.COURSE.getPrefix() + courseEvent.getTitle());
                    assertThat(event.getStart()).isEqualTo(courseEvent.getStart());
                    assertThat(event.getEnd()).isEqualTo(courseEvent.getEnd());
                    assertThat(event.getColor()).isEqualTo(EventCategory.COURSE.getColor());
                });

        // 驗證出隊事件
        assertThat(calendarEvents)
                .anySatisfy(event -> {
                    assertThat(event.getTitle()).isEqualTo(EventCategory.OUTING.getPrefix() + outingEvent.getTitle());
                    assertThat(event.getStart()).isEqualTo(outingEvent.getStart());
                    assertThat(event.getEnd()).isEqualTo(outingEvent.getEnd());
                    assertThat(event.getColor()).isEqualTo(EventCategory.OUTING.getColor());
                });
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
