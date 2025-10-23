package com.web.ncumt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * 代表一篇公告的實體類別。
 */
@Entity
@Data
@FieldNameConstants
public class Post {

    /**
     * 公告的唯一識別碼。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 發表公告的使用者 ID。
     */
    private String userId;

    /**
     * 類型（0 = 隊伍, 1 = 社課, 2 = 其他）
     */
    private Integer type;

    /**
     * 是否置頂 (0 = 否, 1 = 是)
     */
    private Short pin;

    /**
     * 公告標題。
     */
    private String title;

    /**
     * 公告內容。
     */
    private String content;

    /**
     * 公告過期時間。
     */
    private LocalDateTime expiredAt;

    /**
     * 公告建立時間。
     */
    private LocalDateTime createdAt;

    /**
     * 公告最後更新時間。
     */
    private LocalDateTime updatedAt;

    /**
     * 建立此公告的使用者 ID。
     */
    private Long createUser;

    /**
     * 最後修改此公告的使用者 ID。
     */
    private Long modifyUser;
}