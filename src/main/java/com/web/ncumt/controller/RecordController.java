package com.web.ncumt.controller;
import com.web.ncumt.entity.Record;
import com.web.ncumt.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordController {

    @Autowired
    @SuppressWarnings("unused")
    private RecordService recordService;

    @GetMapping("/api/records/latest")
    public List<Record> getLatestRecords(@RequestParam(defaultValue = "10") int limit) {
        return recordService.listLatestRecord(limit);
    }
}
