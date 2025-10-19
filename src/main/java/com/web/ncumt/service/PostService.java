package com.web.ncumt.service;

import com.web.ncumt.entity.Post;

import java.util.List;

/**
 * 處理與文章相關的業務邏輯。
 */
public interface PostService {

    /**
     * 取得所有有效的文章列表。
     * <p>
     * 這個方法會過濾掉所有已經過期的文章，只回傳目前仍然有效的文章。
     * 一篇文章如果其 {@code expiredAt} 欄位為 {@code null} 或是在未來，則被視為有效。
     * </p>
     *
     * @return 一個包含所有有效 {@link Post} 物件的列表。
     */
    List<Post> listActivePost();
}
