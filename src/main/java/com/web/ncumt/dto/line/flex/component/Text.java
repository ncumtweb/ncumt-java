package com.web.ncumt.dto.line.flex.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.ncumt.dto.line.flex.enums.FlexFontSize;
import com.web.ncumt.dto.line.flex.enums.FlexFontWeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Text implements FlexComponent {
    private final String type = "text";
    private String text;
    private Boolean wrap;
    private FlexFontWeight weight;
    private FlexFontSize size;
    private Integer flex;
}
