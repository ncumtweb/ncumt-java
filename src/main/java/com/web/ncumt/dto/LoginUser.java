package com.web.ncumt.dto;

import com.web.ncumt.enums.Role;
import lombok.Builder;
import lombok.Data;

/**
 * 用於儲存當前登入使用者資訊的資料傳輸物件 (DTO)。
 */
@Data
@Builder
public class LoginUser {
    /**
     * 使用者的中文姓名。
     */
    private String nameZh;
    /**
     * 使用者的角色。
     */
    private Role role;
}
