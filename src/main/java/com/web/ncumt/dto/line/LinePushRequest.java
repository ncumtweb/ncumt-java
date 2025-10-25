package com.web.ncumt.dto.line;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinePushRequest {
    private String to;
    private List<Message> messages; // 將類型改為通用的 List<Message>
}
