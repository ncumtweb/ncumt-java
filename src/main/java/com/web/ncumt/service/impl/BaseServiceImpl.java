package com.web.ncumt.service.impl;

import com.web.ncumt.entity.BaseEntity;
import com.web.ncumt.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 通用 Service 實作，提供基礎 CRUD 功能。
 *
 * @param <E> 實體類型
 * @param <R> Repository 類型
 */
public abstract class BaseServiceImpl<E extends BaseEntity, R extends JpaRepository<E, Long>> implements BaseService<E> {

    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
