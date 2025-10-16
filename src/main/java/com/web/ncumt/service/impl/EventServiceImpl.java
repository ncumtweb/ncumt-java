package com.web.ncumt.service.impl;

import com.web.ncumt.dto.CalendarEvent;
import com.web.ncumt.entity.Event;
import com.web.ncumt.enums.EventCategory;
import com.web.ncumt.repository.EventRepository;
import com.web.ncumt.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<CalendarEvent> listIndexCalendarEvent() {
        List<Event> eventList = eventRepository.findAll();
        List<CalendarEvent> calendarEventList = new ArrayList<>();

        for (Event event : eventList) {
            EventCategory eventCategory = EventCategory.getByCategoryIndex(event.getCategory());

            String title = eventCategory.getPrefix() + event.getTitle();

            CalendarEvent dto = CalendarEvent.builder()
                    .id(event.getId())
                    .title(title)
                    .start(event.getStart())
                    .end(event.getEnd())
                    .color(eventCategory.getColor())
                    .build();

            calendarEventList.add(dto);
        }
        return calendarEventList;
    }
}
