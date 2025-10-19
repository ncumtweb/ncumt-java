package com.web.ncumt.dto;

import lombok.Data;

/**
 * 從 NCU OAuth2 Provider 獲取的使用者資訊的資料傳輸物件 (DTO)。
 */
@Data
public class NcuUser {
    /**
     * NCU Portal 的唯一識別碼。
     */
    private String id;
    /**
     * 使用者識別名稱。
     */
    private String identifier;
    /**
     * 帳號類型。
     */
    private String accountType;
    /**
     * 中文姓名。
     */
    private String chineseName;
    /**
     * 英文姓名。
     */
    private String englishName;
    /**
     * 性別。
     */
    private String gender;
    /**
     * 生日。
     */
    private String birthday;
    /**
     * 身分證號碼。
     */
    private String personalId;
    /**
     * 學號。
     */
    private String studentId;
    /**
     * 電子郵件地址。
     */
    private String email;
    /**
     * 電子郵件是否已驗證。
     */
    private boolean emailVerified;
    /**
     * 手機號碼。
     */
    private String mobilePhone;
    /**
     * 手機號碼是否已驗證。
     */
    private boolean mobilePhoneVerified;
}
