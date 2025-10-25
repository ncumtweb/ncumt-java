package com.web.ncumt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 基礎實體類別，包含所有實體共用的欄位。
 */
@Data
@MappedSuperclass
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * 唯一識別碼。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 建立時間。
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * 最後更新時間。
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * 建立此實體的使用者 ID。
     */
    @CreatedBy
    private Long createUser;

    /**
     * 最後修改此實體的使用者 ID。
     */
    @LastModifiedBy
    private Long modifyUser;
}
