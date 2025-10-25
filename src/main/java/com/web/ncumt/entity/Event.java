package com.web.ncumt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 代表一個事件的實體類別。
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Event extends BaseEntity {

    /**
     * 事件的標題。
     */
    private String title;

    /**
     * 事件的開始時間。
     */
    @Column(name = "\"start\"")
    private LocalDateTime start;

    /**
     * 事件的結束時間。
     */
    @Column(name = "\"end\"")
    private LocalDateTime end;

    /**
     * 事件的分類。
     */
    private Integer category;

}
