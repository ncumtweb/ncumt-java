package com.web.ncumt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUser {
    private String nameZh;
    private Integer role;
}
