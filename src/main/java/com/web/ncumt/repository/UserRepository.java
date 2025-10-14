package com.web.ncumt.repository;

import com.web.ncumt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User 資料表的 Repository，提供 CRUD 操作。
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根據電子信箱尋找使用者。
     *
     * @param email 使用者的電子信箱
     * @return 一個包含使用者的 Optional，如果找不到則為空
     */
    Optional<User> findByEmail(String email);
}
