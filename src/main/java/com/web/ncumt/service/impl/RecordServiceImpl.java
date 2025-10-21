package com.web.ncumt.service.impl;

import com.web.ncumt.dto.RecordDTO;
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
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<RecordDTO> listLatestRecord(int limit) {
        List<Record> records = recordRepository.findAllByOrderByStartDateDesc(PageRequest.of(0, limit));
        return records.stream()
                .map(RecordDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
