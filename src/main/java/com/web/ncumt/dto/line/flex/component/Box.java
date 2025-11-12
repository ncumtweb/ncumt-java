package com.web.ncumt.dto.line.flex.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.ncumt.dto.line.flex.enums.FlexLayout;
import com.web.ncumt.dto.line.flex.enums.FlexSpacing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Box implements FlexComponent {
    private final String type = "box";
    private FlexLayout layout;
    private List<FlexComponent> contentList;
    private FlexSpacing spacing; // <--- 新增 spacing 欄位
}
