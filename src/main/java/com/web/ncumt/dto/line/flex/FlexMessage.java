package com.web.ncumt.dto.line.flex;

import com.web.ncumt.dto.line.Message;
import com.web.ncumt.dto.line.flex.container.Bubble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlexMessage implements Message { // 實作 Message 介面
    private final String type = "flex";
    private String altText;
    private Bubble contents;
}
