package com.web.ncumt.service.impl;

import com.web.ncumt.dto.RecordFront;
import com.web.ncumt.entity.Record;
import com.web.ncumt.repository.RecordRepository;
import com.web.ncumt.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class RecordServiceImpl extends BaseServiceImpl<Record, RecordRepository> implements RecordService {

    @Autowired
    public RecordServiceImpl(RecordRepository repository) {
        super(repository);
    }

    @Override
    public List<RecordFront> listLatestRecord(int limit) {
        List<Record> records = repository.findAllByOrderByStartDateDesc(PageRequest.of(0, limit));
        return records.stream()
                .map(RecordFront::fromEntity)
                .collect(Collectors.toList());
    }
}
