package com.web.ncumt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 代表一筆登山紀錄紀錄的實體類別。
 */
@Entity
@Data
public class Record {

    /**
     * 紀錄的唯一識別碼。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 登山紀錄名稱。
     */
    private String name;

    /**
     * 登山紀錄圖片的 URL。
     */
    private String image;

    /**
     * 登山紀錄內容。
     */
    private String content;

    /**
     * 紀錄建立時間。
     */
    private LocalDateTime createdAt;

    /**
     * 紀錄最後更新時間。
     */
    private LocalDateTime updatedAt;

    /**
     * 種類（0 = 中級山, 1 = 高山, 2 = 溯溪）
     */
    private Short category;

    /**
     * 開始日期。
     */
    private LocalDate startDate;

    /**
     * 結束日期。
     */
    private LocalDate endDate;

    /**
     * 登山紀錄描述。
     */
    private String description;

    /**
     * 建立此紀錄的使用者 ID。
     */
    private Long createUser;

    /**
     * 最後修改此紀錄的使用者 ID。
     */
    private Long modifyUser;
}