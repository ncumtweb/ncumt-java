package com.web.ncumt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime start;

    private LocalDateTime end;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer category;

    private Long createUser;

    private Long modifyUser;
}
