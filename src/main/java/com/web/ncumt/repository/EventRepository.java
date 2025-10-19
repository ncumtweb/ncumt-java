package com.web.ncumt.repository;

import com.web.ncumt.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用於存取 {@link Event} 實體的 Spring Data JPA Repository。
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}