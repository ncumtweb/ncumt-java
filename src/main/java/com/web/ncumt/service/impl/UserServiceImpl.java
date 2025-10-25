package com.web.ncumt.service.impl;

import com.web.ncumt.dto.user.NcuUser;
import com.web.ncumt.entity.User;
import com.web.ncumt.repository.UserRepository;
import com.web.ncumt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * UserService 的實作，處理使用者相關的業務邏輯。
 */
@Service
@SuppressWarnings("unused")
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findOrCreateUser(NcuUser ncuUser) {
        // 根據 email 尋找使用者
        return repository.findByEmail(ncuUser.getEmail())
                .map(user -> {
                    // 如果使用者已存在，則更新其資訊
                    updateUserData(user, ncuUser);
                    // 儲存並返回更新後的使用者
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    // 如果使用者不存在，則建立一個新使用者
                    User newUser = new User();
                    updateUserData(newUser, ncuUser);
                    newUser.setCreatedAt(LocalDateTime.now());
                    newUser.setUpdatedAt(LocalDateTime.now());
                    // 儲存並返回新建立的使用者
                    return repository.save(newUser);
                });
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * 使用 NcuUserDTO 的資料更新 User 實體。
     *
     * @param user       要更新的 User 實體
     * @param ncuUser 包含新資料的 DTO
     */
    private void updateUserData(User user, NcuUser ncuUser) {
        user.setEmail(ncuUser.getEmail());
        user.setNameZh(ncuUser.getChineseName());
        user.setStudentId(ncuUser.getStudentId());
        user.setNameEn(ncuUser.getEnglishName());
        if (ncuUser.getGender() != null) {
            user.setGender(Short.parseShort(ncuUser.getGender()));
        }
        user.setBirthday(ncuUser.getBirthday());
        user.setPersonalId(ncuUser.getPersonalId());
        user.setPhone(ncuUser.getMobilePhone());
    }
}
