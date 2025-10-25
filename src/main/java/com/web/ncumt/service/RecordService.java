package com.web.ncumt.service;

import com.web.ncumt.dto.RecordFront;
import com.web.ncumt.entity.Record;

import java.util.List;

public interface RecordService extends BaseService<Record> {

    /**
     * @param limit 要回傳的紀錄數量
     * @return 最近的紀錄列表
     */
    List<RecordFront> listLatestRecord(int limit);
}
