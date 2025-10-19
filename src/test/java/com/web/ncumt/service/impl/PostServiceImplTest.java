package com.web.ncumt.service.impl;

import com.web.ncumt.entity.Post;
import com.web.ncumt.repository.PostRepository;
import com.web.ncumt.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link PostServiceImpl} 的整合測試，使用 H2 資料庫。
 */
@SpringBootTest
@SuppressWarnings("unused")
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        // 每次測試前清空資料庫，確保測試之間互相獨立
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("當有多種文章時，應只回傳有效的文章")
    void listActivePost_shouldReturnOnlyActivePosts() {
        // 編排
        Post activePost = new Post();
        activePost.setTitle("未來過期的文章");
        activePost.setExpiredAt(LocalDateTime.now().plusDays(1));
        activePost.setPin((short) 0);
        activePost.setCreatedAt(LocalDateTime.now().minusHours(1));

        Post expiredPost = new Post();
        expiredPost.setTitle("已過期的文章");
        expiredPost.setExpiredAt(LocalDateTime.now().minusDays(1));
        expiredPost.setPin((short) 0);
        expiredPost.setCreatedAt(LocalDateTime.now().minusHours(2));

        postRepository.saveAll(List.of(activePost, expiredPost));

        // 行動
        List<Post> activePostList = postService.listActivePost();

        // 斷言
        assertThat(activePostList)
                .hasSize(1)
                .extracting(Post::getTitle)
                .containsExactly("未來過期的文章");
    }

    @Test
    @DisplayName("當資料庫沒有任何文章時，應回傳空列表")
    void listActivePost_whenNoPosts_shouldReturnEmptyList() {
        // 編排 - 不需要，因為 @BeforeEach 已經清空了資料庫

        // 行動
        List<Post> activePostList = postService.listActivePost();

        // 斷言
        assertThat(activePostList).isEmpty();
    }

    @Test
    @DisplayName("當所有文章都已過期時，應回傳空列表")
    void listActivePost_whenAllPostsAreExpired_shouldReturnEmptyList() {
        // 編排
        Post expiredPost1 = new Post();
        expiredPost1.setTitle("已過期的文章 1");
        expiredPost1.setExpiredAt(LocalDateTime.now().minusDays(1));
        expiredPost1.setPin((short) 0);
        expiredPost1.setCreatedAt(LocalDateTime.now().minusHours(1));

        Post expiredPost2 = new Post();
        expiredPost2.setTitle("已過期的文章 2");
        expiredPost2.setExpiredAt(LocalDateTime.now().minusMinutes(10));
        expiredPost2.setPin((short) 0);
        expiredPost2.setCreatedAt(LocalDateTime.now().minusHours(2));

        postRepository.saveAll(List.of(expiredPost1, expiredPost2));

        // 行動
        List<Post> activePostList = postService.listActivePost();

        // 斷言
        assertThat(activePostList).isEmpty();
    }

    @Test
    @DisplayName("應根據 Pin (置頂) 和 CreatedAt (建立時間) 正確排序有效的文章")
    void listActivePost_shouldSortActivePostsCorrectly() {
        // 編排
        Post post1 = new Post(); // 有效，未置頂，較新
        post1.setTitle("文章1");
        post1.setExpiredAt(LocalDateTime.now().plusDays(1));
        post1.setPin((short) 0);
        post1.setCreatedAt(LocalDateTime.now().minusMinutes(10));

        Post post2 = new Post(); // 有效，置頂，較舊
        post2.setTitle("文章2-置頂");
        post2.setExpiredAt(LocalDateTime.now().plusDays(1));
        post2.setPin((short) 1);
        post2.setCreatedAt(LocalDateTime.now().minusHours(1));

        Post post3 = new Post(); // 有效，未置頂，最舊
        post3.setTitle("文章3");
        post3.setExpiredAt(LocalDateTime.now().plusDays(1));
        post3.setPin((short) 0);
        post3.setCreatedAt(LocalDateTime.now().minusHours(2));

        Post post4_expired = new Post(); // 過期，但置頂
        post4_expired.setTitle("文章4-過期置頂");
        post4_expired.setExpiredAt(LocalDateTime.now().minusSeconds(1));
        post4_expired.setPin((short) 1);
        post4_expired.setCreatedAt(LocalDateTime.now());

        postRepository.saveAll(List.of(post1, post2, post3, post4_expired));

        // 行動
        List<Post> activePostList = postService.listActivePost();

        // 斷言: 預期順序 -> 置頂的優先 (post2), 然後根據建立時間倒序 (post1, post3)
        assertThat(activePostList)
                .hasSize(3)
                .extracting(Post::getTitle)
                .containsExactly("文章2-置頂", "文章1", "文章3");
    }
}
