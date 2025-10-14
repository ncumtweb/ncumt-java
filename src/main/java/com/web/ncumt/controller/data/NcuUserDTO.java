package com.web.ncumt.controller.data;

import lombok.Data;

/**
 * 從 NCU OAuth2 Provider 獲取的使用者資訊的資料傳輸物件 (DTO)。
 */
@Data
public class NcuUserDTO {
    private String id;
    private String identifier;
    private String accountType;
    private String chineseName;
    private String englishName;
    private String gender;
    private String birthday;
    private String personalId;
    private String studentId;
    private String email;
    private boolean emailVerified;
    private String mobilePhone;
    private boolean mobilePhoneVerified;
}
