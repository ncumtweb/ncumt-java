package com.web.ncumt.repository;

import com.web.ncumt.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 根據過期時間查找分頁後的公告。
     * Spring Data JPA 會自動應用 Pageable 中的分頁和排序規則。
     *
     * @param now      當前時間，用於過濾過期公告
     * @param pageable 分頁和排序資訊
     * @return 分頁後的 Post 物件
     */
    Page<Post> findByExpiredAtAfter(LocalDateTime now, Pageable pageable);
}
