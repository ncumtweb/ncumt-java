package com.web.ncumt.service;

import com.web.ncumt.entity.Record;

import java.util.List;

public interface RecordService {

    /**
     * @param limit 要回傳的紀錄數量
     * @return 最近的紀錄列表
     */
    List<Record> listLatestRecord(int limit);
}
