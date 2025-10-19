package com.web.ncumt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 代表一個事件的實體類別。
 */
@Entity
@Data
public class Event {

    /**
     * 事件的唯一識別碼。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     * 事件的建立時間。
     */
    private LocalDateTime createdAt;

    /**
     * 事件的最後更新時間。
     */
    private LocalDateTime updatedAt;

    /**
     * 事件的分類。
     */
    private Integer category;

    /**
     * 建立此事件的使用者 ID。
     */
    private Long createUser;

    /**
     * 最後修改此事件的使用者 ID。
     */
    private Long modifyUser;
}
