package com.web.ncumt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用於在日曆上顯示活動的資料傳輸物件 (DTO)。
 */
@Data
@Builder
public class CalendarEvent {

    /**
     * 活動的唯一識別碼。
     */
    private Long id;

    /**
     * 活動的標題。
     */
    private String title;

    /**
     * 活動的開始時間。
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    /**
     * 活動的結束時間。
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    /**
     * 活動在日曆上顯示的顏色。
     */
    private String color;
}
