package com.web.ncumt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * 使用者實體，對應到 users 資料表。
 */
@Data
@Entity
@Table(name = "\"user\"")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    /**
     * 學號
     */
    private String studentId;

    /**
     * 中文名稱
     */
    private String nameZh;

    /**
     * 電子信箱
     */
    private String email;

    /**
     * 英文名字
     */
    private String nameEn;

    /**
     * 暱稱
     */
    private String nickname;

    /**
     * 手機
     */
    private String phone;

    /**
     * 角色
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer role = 0;

    /**
     * Telegram Token
     */
    private Integer tokenTg;

    /**
     * LINE Token
     */
    private Integer tokenLine;

    /**
     * Remember Token
     */
    private String rememberToken;

    /**
     * 系級
     */
    private String departmentLevel;

    /**
     * 性別 0：男, 1：女
     */
    private Short gender;

    /**
     * 高山症 0：沒有, 1：輕微, 2：嚴重
     */
    private Short altitudeSickness;

    /**
     * 特殊疾病(加說明)
     */
    private String specialDisease;

    /**
     * 葷素食調查 0：葷, 1：素, 2：蛋奶素
     */
    private Short dietaryHabit;

    /**
     * 喜歡的食物們
     */
    private String favoriteFoods;

    /**
     * 過敏的食物們
     */
    private String allergicFoods;

    /**
     * 討厭的食物們
     */
    private String hateFoods;

    /**
     * 緊急聯絡人
     */
    private String emergencyContactPerson;

    /**
     * 與緊急聯絡人關係
     */
    private String emergencyContactRelation;

    /**
     * 緊急聯絡電話
     */
    private Long emergencyContactPhone;

    /**
     * 家裡電話
     */
    private Long homePhoneNumber;

    /**
     * 家裡住址
     */
    private String homeAddress;

    /**
     * 在山上的天數
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer daysInMountain = 0;

    /**
     * 爬山的次數
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer timesClimbedMountain = 0;

    /**
     * 五公里跑步時間
     */
    private Time fiveKilogramsRunningTime;

    /**
     * 加入登山社時間
     */
    private LocalDateTime joinTheClubTime;

    /**
     * 身分證字號
     */
    private String personalId;

    /**
     * 0：非嚮導, 1：初嚮, 2：領嚮
     */
    private Short guard;

    /**
     * 是否有長程經驗
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean hasLongExperience = false;

    /**
     * 是否有溯溪經驗
     */
    @Column(columnDefinition = "boolean default false")
    private Boolean hasRiverExperience = false;

    /**
     * 登入方式
     */
    private String loginMethod;

    /**
     * 0：非溪嚮, 1：溪嚮
     */
    @Column(nullable = false, columnDefinition = "smallint default 0")
    private Short riverGuard = 0;

    /**
     * 生日
     */
    private String birthday;
}
