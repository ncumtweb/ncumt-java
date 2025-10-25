package com.web.ncumt.service.impl;

import com.web.ncumt.config.IntegrationTest;
import com.web.ncumt.dto.RecordFront;
import com.web.ncumt.entity.Record;
import com.web.ncumt.repository.RecordRepository;
import com.web.ncumt.service.RecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link RecordServiceImpl} 的整合測試，使用 H2 資料庫。
 */
@IntegrationTest
@SuppressWarnings("unused")
class RecordServiceImplTest {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @BeforeEach
    void setUp() {
        recordRepository.deleteAll();
    }

    @Test
    @DisplayName("應根據開始日期回傳最新的紀錄")
    void listLatestRecord_shouldReturnLatestRecordsOrderedByStartDate() {
        // 編排
        Record recentRecord = new Record();
        recentRecord.setName("最近的活動");
        recentRecord.setStartDate(LocalDate.now().minusDays(1));

        Record olderRecord = new Record();
        olderRecord.setName("較舊的活動");
        olderRecord.setStartDate(LocalDate.now().minusDays(10));

        Record oldestRecord = new Record();
        oldestRecord.setName("最舊的活動");
        oldestRecord.setStartDate(LocalDate.now().minusDays(20));

        recordRepository.saveAll(List.of(olderRecord, oldestRecord, recentRecord));

        // 行動
        List<RecordFront> latestRecords = recordService.listLatestRecord(2);

        // 斷言
        assertThat(latestRecords)
                .hasSize(2)
                .extracting(RecordFront::getName)
                .containsExactly("最近的活動", "較舊的活動");
    }

    @Test
    @DisplayName("當請求的數量超過實際紀錄數量時，應回傳所有紀錄")
    void listLatestRecord_whenLimitIsGreaterThanTotal_shouldReturnAllRecords() {
        // 編排
        Record record1 = new Record();
        record1.setName("活動1");
        record1.setStartDate(LocalDate.now().minusDays(1));

        Record record2 = new Record();
        record2.setName("活動2");
        record2.setStartDate(LocalDate.now().minusDays(2));

        recordRepository.saveAll(List.of(record1, record2));

        // 行動
        List<RecordFront> latestRecords = recordService.listLatestRecord(5);

        // 斷言
        assertThat(latestRecords)
                .hasSize(2)
                .extracting(RecordFront::getName)
                .containsExactly("活動1", "活動2");
    }

    @Test
    @DisplayName("當資料庫沒有任何紀錄時，應回傳空列表")
    void listLatestRecord_whenNoRecords_shouldReturnEmptyList() {
        // 編排 - 資料庫已由 @BeforeEach 清空

        // 行動
        List<RecordFront> latestRecords = recordService.listLatestRecord(5);

        // 斷言
        assertThat(latestRecords).isEmpty();
    }
}
