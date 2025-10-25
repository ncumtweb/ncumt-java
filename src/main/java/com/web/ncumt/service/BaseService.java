package com.web.ncumt.service;

import com.web.ncumt.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * 通用 Service 介面，定義基礎 CRUD 操作。
 *
 * @param <T> 實體類型，必須繼承自 BaseEntity
 */
public interface BaseService<T extends BaseEntity> {

    /**
     * 根據 ID 查找實體。
     *
     * @param id 實體 ID
     * @return Optional<T>
     */
    Optional<T> findById(Long id);

    /**
     * 查找所有實體。
     *
     * @return List<T>
     */
    List<T> findAll();

    /**
     * 保存實體 (新增或更新)。
     *
     * @param entity 要保存的實體
     * @return 保存後的實體
     */
    T save(T entity);

    /**
     * 根據 ID 刪除實體。
     *
     * @param id 實體 ID
     */
    void deleteById(Long id);
}
