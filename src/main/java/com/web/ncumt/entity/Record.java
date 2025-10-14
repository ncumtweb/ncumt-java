package com.web.ncumt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * 種類（0 = 中級山, 1 = 高山, 2 = 溯溪）
     */
    private Short category;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private Long createUser;

    private Long modifyUser;
}