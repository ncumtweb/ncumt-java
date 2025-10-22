package com.web.ncumt.dto;

import com.web.ncumt.entity.Post;
import com.web.ncumt.enums.PostType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用於前端顯示的公告資料傳輸物件。
 */
@Data
public class PostFront {

    private Long id;
    private Short pin;
    private String userId;
    private String title;
    private String content;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private String typeName;
    private String color;

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
        dto.setUserId(post.getUserId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setExpiredAt(post.getExpiredAt());
        dto.setCreatedAt(post.getCreatedAt());

        PostType postType = PostType.fromValue(post.getType());
        dto.setTypeName(postType.getName());
        dto.setColor(postType.getColor());

        return dto;
    }
}
