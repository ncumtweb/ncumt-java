package com.web.ncumt.dto.line.flex.container;

import com.web.ncumt.dto.line.flex.component.Box;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bubble {
    private final String type = "bubble";
    private Box header;
    private Box body;
    private Box footer;
}
