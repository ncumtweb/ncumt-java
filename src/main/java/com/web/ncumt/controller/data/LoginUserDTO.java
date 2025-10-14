package com.web.ncumt.controller.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDTO {
    private String nameZh;
    private Integer role;
    private Boolean isLoginSuccess;
}
