package com.web.ncumt.service;

import com.web.ncumt.dto.RecordDTO;

import java.util.List;

public interface RecordService {

    /**
     * @param limit 要回傳的紀錄數量
     * @return 最近的紀錄列表
     */
    List<RecordDTO> listLatestRecord(int limit);
}
