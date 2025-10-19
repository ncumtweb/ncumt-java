package com.web.ncumt.service.impl;

import com.web.ncumt.dto.NcuUser;
import com.web.ncumt.entity.User;
import com.web.ncumt.repository.UserRepository;
import com.web.ncumt.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link UserServiceImpl} 的整合測試，使用 H2 資料庫。
 */
@SpringBootTest
@SuppressWarnings("unused")
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private NcuUser ncuUserDto;

    @BeforeEach
    void setUp() {
        // 每次測試前清空資料庫
        userRepository.deleteAll();

        // 準備一個通用的 DTO，用於測試
        ncuUserDto = new NcuUser();
        ncuUserDto.setEmail("test@ncu.edu.tw");
        ncuUserDto.setChineseName("測試員");
        ncuUserDto.setStudentId("109502501");
        ncuUserDto.setEnglishName("Test User");
        ncuUserDto.setGender("0");
        ncuUserDto.setBirthday("2000-01-01");
        ncuUserDto.setPersonalId("A123456789");
        ncuUserDto.setMobilePhone("0912345678");
    }

    @Test
    @DisplayName("當使用者不存在時，應能成功建立新使用者")
    void findOrCreateUser_whenUserDoesNotExist_shouldCreateNewUser() {
        // 編排 - 資料庫已由 @BeforeEach 清空
        assertThat(userRepository.count()).isZero();

        // 行動
        User createdUser = userService.findOrCreateUser(ncuUserDto);

        // 斷言
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo(ncuUserDto.getEmail());
        assertThat(createdUser.getNameZh()).isEqualTo(ncuUserDto.getChineseName());
        assertThat(createdUser.getCreatedAt()).isNotNull();
        assertThat(createdUser.getUpdatedAt()).isNotNull();

        // 直接從資料庫驗證
        assertThat(userRepository.count()).isEqualTo(1);
        User userInDb = userRepository.findAll().getFirst();
        assertThat(userInDb.getNameZh()).isEqualTo("測試員");
    }

    @Test
    @DisplayName("當使用者已存在時，應更新其資料而非建立新使用者")
    void findOrCreateUser_whenUserExists_shouldUpdateUser() {
        // 編排 - 先在資料庫中建立一個舊的使用者
        User existingUser = new User();
        existingUser.setEmail("test@ncu.edu.tw");
        existingUser.setNameZh("舊名字");
        existingUser.setStudentId("舊學號");
        userRepository.save(existingUser);

        assertThat(userRepository.count()).isEqualTo(1);

        // 準備一個包含新資料的 DTO
        ncuUserDto.setChineseName("新名字");
        ncuUserDto.setStudentId("109502501");

        // 行動
        User updatedUser = userService.findOrCreateUser(ncuUserDto);

        // 斷言
        assertThat(updatedUser.getId()).isEqualTo(existingUser.getId());
        assertThat(updatedUser.getNameZh()).isEqualTo("新名字");
        assertThat(updatedUser.getStudentId()).isEqualTo("109502501");

        // 直接從資料庫驗證，確保沒有建立新使用者
        assertThat(userRepository.count()).isEqualTo(1);
        User userInDb = userRepository.findById(existingUser.getId()).orElseThrow();
        assertThat(userInDb.getNameZh()).isEqualTo("新名字");
    }
}
