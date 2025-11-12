package com.web.ncumt.dto.line;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineReplyResponse {
    private List<SentMessageInfo> sentMessageList;
}
