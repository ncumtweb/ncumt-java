package com.web.ncumt.service;

import com.web.ncumt.dto.post.PostFront;
import com.web.ncumt.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * 處理與文章相關的業務邏輯。
 */
public interface PostService extends BaseService<Post> {

    /**
     * 取得所有有效的文章列表，並進行分頁。
     * <p>
     * 這個方法會過濾掉所有已經過期的文章，只回傳目前仍然有效的文章。
     * 一篇文章如果其 {@code expiredAt} 欄位為 {@code null} 或是在未來，則被視為有效。
     * </p>
     *
     * @param pageable 分頁和排序資訊
     * @return 一個包含所有有效 {@link PostFront} 物件的 Page。
     */
    Page<PostFront> pageActivePost(Pageable pageable);

    /**
     * 取得所有公告，並將其轉換為用於前端顯示的 DTO 列表。
     * <p>
     * 這個方法的實作考慮了效能，避免了在迴圈中逐一查詢使用者名稱所造成的 N+1 問題。
     * 其主要步驟如下：
     * <ol>
     *     <li>從 postRepository 取得所有 Post 實體。</li>
     *     <li>使用 Stream API 遍歷所有 Post，並透過 flatMap 將每個 Post 的 createUser 和 modifyUser ID 收集到一個 Set 中，以確保 ID 的唯一性。</li>
     *     <li>透過 userRepository.findAllById() 方法，一次性地從資料庫中查詢出所有相關的 User 實體。</li>
     *     <li>將查詢到的 User 列表轉換為一個 Map&lt;Long, String&gt;，其中 Key 是使用者 ID，Value 是使用者名稱 (nameZh)。</li>
     *     <li>再次遍歷 Post 列表，將每個 Post 實體轉換為 PostFront DTO。</li>
     *     <li>在轉換過程中，使用先前建立的 Map 來查詢建立者和編輯者的名稱，並設定到 DTO 的對應欄位中。如果 Map 中找不到對應的 ID (例如，使用者已被刪除)，則會使用「未知使用者」作為預設值。</li>
     *     <li>最後，將所有處理完成的 PostFront DTO 收集到一個 List 中並回傳。</li>
     * </ol>
     * </p>
     *
     * @param pageable 分頁資訊
     * @return 一個包含所有 {@link PostFront} 物件的 Page，其中包含了格式化後的日期和使用者名稱。
     */
    Page<PostFront> pageAllPost(Pageable pageable);

    Optional<PostFront> getPostFrontById(Long id);
}
