package com.web.ncumt.service;

import com.web.ncumt.dto.CalendarEvent;
import com.web.ncumt.entity.Event;

import java.util.List;

/**
 * 處理與行事曆相關的業務邏輯。
 */
public interface EventService extends BaseService<Event> {

    /**
     * 取得所有活動，並將其轉換為適合首頁日曆顯示的格式。
     * <p>
     * 這個方法會查詢資料庫中所有的 {@link com.web.ncumt.entity.Event} 實體，
     * 並將它們逐一轉換為 {@link CalendarEvent} DTO。
     * 轉換過程中，會根據活動的分類 ({@link com.web.ncumt.enums.EventCategory})
     * 為活動標題加上前綴，並設定對應的顏色。
     * </p>
     *
     * @return 一個包含 {@link CalendarEvent} 物件的列表，用於 FullCalendar 的活動來源。
     */
    List<CalendarEvent> listIndexCalendarEvent();
}
