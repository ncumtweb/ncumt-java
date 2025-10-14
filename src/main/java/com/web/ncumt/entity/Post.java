package com.web.ncumt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    /**
     * 類型（0 = 隊伍, 1 = 社課, 2 = 其他）
     */
    private Integer type;

    /**
     * 是否置頂 (0 = 否, 1 = 是)
     */
    private Short pin;

    private String title;

    private String content;

    private LocalDateTime expiredAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long createUser;

    private Long modifyUser;
}