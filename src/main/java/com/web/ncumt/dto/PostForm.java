package com.web.ncumt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 用於接收前端創建公告表單數據的 DTO。
 */
@Data
public class PostForm {

    @NotBlank(message = "公告標題不能為空")
    private String title;

    @NotNull(message = "請選擇公告類別")
    private Integer type;

    @NotNull(message = "請選擇是否置頂")
    private Short pin;

    @NotNull(message = "請選擇過期時間")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime expiredAt;

    @NotBlank(message = "公告內容不能為空")
    private String content;
}
