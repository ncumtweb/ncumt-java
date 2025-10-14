package com.web.ncumt.repository;

import com.web.ncumt.entity.Record;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByOrderByStartDateDesc(Pageable pageable);
}
