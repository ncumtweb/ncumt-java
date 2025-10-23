package com.web.ncumt.dto;

import lombok.Data;

@Data
public class FrontendError {
    private String name;
    private String message;
    private String stack;
}
