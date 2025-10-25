package com.web.ncumt.dto.post;

import com.web.ncumt.constant.DateTimeFormatConstant;
import com.web.ncumt.entity.Post;
import com.web.ncumt.enums.PostType;
import lombok.Data;

import java.util.Map;

/**
 * 用於前端顯示的公告資料傳輸物件。
 */
@Data
public class PostFront {

    private Long id;
    private Short pin;
    private String title;
    private String content;
    private String expiredAt;
    private String createdAt;
    private String updatedAt;
    private String typeName;
    private String color;
    private String createUserName;
    private String modifyUserName;

    /**
     * 從 Post 轉換成 PostFront
     *
     * @param post            Post 實體
     * @param userIdToNameMap 使用者 ID 到使用者名稱的映射
     * @return PostFront 物件
     */
    public static PostFront fromEntity(Post post, Map<Long, String> userIdToNameMap) {
        PostFront dto = fromEntity(post);
        dto.setCreateUserName(userIdToNameMap.getOrDefault(post.getCreateUser(), "未知使用者"));
        dto.setModifyUserName(userIdToNameMap.getOrDefault(post.getModifyUser(), "未知使用者"));

        return dto;
    }

    /**
     * 從 Post 實體轉換為 PostFront。
     *
     * @param post Post 實體
     * @return PostFront 物件
     */
    public static PostFront fromEntity(Post post) {
        PostFront dto = new PostFront();
        dto.setId(post.getId());
        dto.setPin(post.getPin());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt() != null ? post.getCreatedAt().format(DateTimeFormatConstant.YYYY_MM_DD_HH_MM) : "");
        dto.setUpdatedAt(post.getUpdatedAt() != null ? post.getUpdatedAt().format(DateTimeFormatConstant.YYYY_MM_DD_HH_MM) : "");
        dto.setExpiredAt(post.getExpiredAt() != null ? post.getExpiredAt().format(DateTimeFormatConstant.YYYY_MM_DD_HH_MM) : "");
        PostType postType = PostType.fromValue(post.getType());
        dto.setTypeName(postType.getName());
        dto.setColor(postType.getColor());
        return dto;
    }
}
