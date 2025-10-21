package com.web.ncumt.dto;

import com.web.ncumt.entity.Record;
import com.web.ncumt.enums.RecordCategory;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用於前端顯示的登山紀錄資料傳輸物件。
 */
@Data
public class RecordDTO {

    private Long id;
    private String name;
    private String image;
    private String content;
    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String categoryName;

    /**
     * 從 Record 實體轉換為 RecordDTO。
     *
     * @param record Record 實體
     * @return RecordDTO 物件
     */
    public static RecordDTO fromEntity(Record record) {
        RecordDTO dto = new RecordDTO();
        dto.setId(record.getId());
        dto.setName(record.getName());
        dto.setImage(record.getImage());
        dto.setContent(record.getContent());
        dto.setCreatedAt(record.getCreatedAt());
        dto.setStartDate(record.getStartDate());
        dto.setEndDate(record.getEndDate());
        dto.setDescription(record.getDescription());

        // 將 category 的 Short 值轉換為對應的顯示名稱
        RecordCategory recordCategory = RecordCategory.fromValue(record.getCategory());
        dto.setCategoryName(recordCategory.getName());

        return dto;
    }
}
