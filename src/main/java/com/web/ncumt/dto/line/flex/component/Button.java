package com.web.ncumt.dto.line.flex.component;

import com.web.ncumt.dto.line.UriAction;
import com.web.ncumt.dto.line.flex.enums.FlexButtonStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Button implements FlexComponent {
    private final String type = "button";
    private UriAction action;
    private FlexButtonStyle style;
}
