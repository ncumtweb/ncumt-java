package com.web.ncumt.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 代表一筆登山紀錄的實體類別。
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Record extends BaseEntity {

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

}
