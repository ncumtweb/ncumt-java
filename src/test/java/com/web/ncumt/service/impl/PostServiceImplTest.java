package com.web.ncumt.service.impl;

import com.web.ncumt.dto.PostFront;
import com.web.ncumt.entity.Post;
import com.web.ncumt.entity.User;
import com.web.ncumt.repository.PostRepository;
import com.web.ncumt.repository.UserRepository;
import com.web.ncumt.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SuppressWarnings("unused")
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();

        // 建立一個共用的測試使用者
        testUser = new User();
        testUser.setNameZh("測試人員");
        testUser.setStudentId("109502501");
        testUser = userRepository.save(testUser);
    }

    @Test
    @DisplayName("當 ID 存在且文章未過期時，應回傳包含正確 PostFront 的 Optional")
    void getPostFrontById_whenPostExistsAndIsActive_shouldReturnPostFront() {
        // 編排
        Post activePost = new Post();
        activePost.setTitle("一篇有效的文章");
        activePost.setContent("文章內容");
        activePost.setExpiredAt(LocalDateTime.now().plusDays(1));
        activePost.setCreateUser(testUser.getId());
        activePost.setModifyUser(testUser.getId());
        activePost = postRepository.save(activePost);

        // 行動
        Optional<PostFront> result = postService.getPostFrontById(activePost.getId());

        // 斷言
        assertThat(result).isPresent();
        result.ifPresent(postFront -> {
            assertThat(postFront.getTitle()).isEqualTo("一篇有效的文章");
            assertThat(postFront.getContent()).isEqualTo("文章內容");
            assertThat(postFront.getCreateUserName()).isEqualTo("測試人員");
        });
    }

    @Test
    @DisplayName("當 ID 不存在時，應回傳空的 Optional")
    void getPostFrontById_whenPostNotFound_shouldReturnEmpty() {
        // 編排
        long nonExistentId = 999L;

        // 行動
        Optional<PostFront> result = postService.getPostFrontById(nonExistentId);

        // 斷言
        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("當有多種文章時，應只回傳有效的文章並包含正確的使用者名稱")
    void pageActivePost_shouldReturnOnlyActivePosts() {
        // 編排
        Post activePost = new Post();
        activePost.setTitle("未來過期的文章");
        activePost.setExpiredAt(LocalDateTime.now().plusDays(1));
        activePost.setCreateUser(testUser.getId());
        activePost.setModifyUser(testUser.getId());

        Post expiredPost = new Post();
        expiredPost.setTitle("已過期的文章");
        expiredPost.setExpiredAt(LocalDateTime.now().minusDays(1));
        expiredPost.setCreateUser(testUser.getId());

        postRepository.saveAll(List.of(activePost, expiredPost));

        Pageable pageable = PageRequest.of(0, 10);

        // 行動
        Page<PostFront> resultPage = postService.pageActivePost(pageable);

        // 斷言
        assertThat(resultPage.getTotalElements()).isEqualTo(1);
        assertThat(resultPage.getContent()).hasSize(1);
        PostFront resultPost = resultPage.getContent().getFirst();
        assertThat(resultPost.getTitle()).isEqualTo("未來過期的文章");
        assertThat(resultPost.getCreateUserName()).isEqualTo("測試人員");
    }

    @Test
    @DisplayName("當資料庫沒有任何文章時，應回傳空的 Page 物件")
    void pageActivePost_whenNoPosts_shouldReturnEmptyPage() {
        // 編排
        Pageable pageable = PageRequest.of(0, 10);

        // 行動
        Page<PostFront> resultPage = postService.pageActivePost(pageable);

        // 斷言
        assertThat(resultPage.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("當所有文章都已過期時，應回傳空的 Page 物件")
    void pageActivePost_whenAllPostsAreExpired_shouldReturnEmptyPage() {
        // 編排
        Post expiredPost1 = new Post();
        expiredPost1.setTitle("已過期的文章 1");
        expiredPost1.setExpiredAt(LocalDateTime.now().minusDays(1));
        expiredPost1.setCreateUser(testUser.getId());

        Post expiredPost2 = new Post();
        expiredPost2.setTitle("已過期的文章 2");
        expiredPost2.setExpiredAt(LocalDateTime.now().minusMinutes(10));
        expiredPost2.setCreateUser(testUser.getId());

        postRepository.saveAll(List.of(expiredPost1, expiredPost2));

        Pageable pageable = PageRequest.of(0, 10);

        // 行動
        Page<PostFront> resultPage = postService.pageActivePost(pageable);

        // 斷言
        assertThat(resultPage.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("應根據 Pin (置頂) 和 CreatedAt (建立時間) 正確排序有效的文章")
    void pageActivePost_shouldSortActivePostsCorrectly() {
        // 編排
        Post post1 = new Post(); // 有效，未置頂，較新
        post1.setTitle("文章1");
        post1.setExpiredAt(LocalDateTime.now().plusDays(1));
        post1.setPin((short) 0);
        post1.setCreatedAt(LocalDateTime.now().minusMinutes(10));
        post1.setCreateUser(testUser.getId());

        Post post2 = new Post(); // 有效，置頂，較舊
        post2.setTitle("文章2-置頂");
        post2.setExpiredAt(LocalDateTime.now().plusDays(1));
        post2.setPin((short) 1);
        post2.setCreatedAt(LocalDateTime.now().minusHours(1));
        post2.setCreateUser(testUser.getId());

        Post post3 = new Post(); // 有效，未置頂，最舊
        post3.setTitle("文章3");
        post3.setExpiredAt(LocalDateTime.now().plusDays(1));
        post3.setPin((short) 0);
        post3.setCreatedAt(LocalDateTime.now().minusHours(2));
        post3.setCreateUser(testUser.getId());

        Post post4_expired = new Post(); // 過期，但置頂
        post4_expired.setTitle("文章4-過期置頂");
        post4_expired.setExpiredAt(LocalDateTime.now().minusSeconds(1));
        post4_expired.setPin((short) 1);
        post4_expired.setCreatedAt(LocalDateTime.now());
        post4_expired.setCreateUser(testUser.getId());

        postRepository.saveAll(List.of(post1, post2, post3, post4_expired));

        // 模擬 Controller 的排序請求
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "pin", "createdAt"));

        // 行動
        Page<PostFront> resultPage = postService.pageActivePost(pageable);

        // 斷言: 預期順序 -> 置頂的優先 (post2), 然後根據建立時間倒序 (post1, post3)
        assertThat(resultPage.getTotalElements()).isEqualTo(3);
        assertThat(resultPage.getContent())
                .extracting(PostFront::getTitle)
                .containsExactly("文章2-置頂", "文章1", "文章3");
    }
}
