package com.web.ncumt.service.impl;

import com.web.ncumt.controller.data.NcuUserDTO;
import com.web.ncumt.entity.User;
import com.web.ncumt.repository.UserRepository;
import com.web.ncumt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * UserService 的實作，處理使用者相關的業務邏輯。
 */
@Service
@SuppressWarnings("unused")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findOrCreateUser(NcuUserDTO ncuUserDTO) {
        // 根據 email 尋找使用者
        return userRepository.findByEmail(ncuUserDTO.getEmail())
                .map(user -> {
                    // 如果使用者已存在，則更新其資訊
                    updateUserData(user, ncuUserDTO);
                    user.setUpdatedAt(LocalDateTime.now());
                    // 儲存並返回更新後的使用者
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    // 如果使用者不存在，則建立一個新使用者
                    User newUser = new User();
                    updateUserData(newUser, ncuUserDTO);
                    newUser.setCreatedAt(LocalDateTime.now());
                    newUser.setUpdatedAt(LocalDateTime.now());
                    // 儲存並返回新建立的使用者
                    return userRepository.save(newUser);
                });
    }

    /**
     * 使用 NcuUserDTO 的資料更新 User 實體。
     *
     * @param user       要更新的 User 實體
     * @param ncuUserDTO 包含新資料的 DTO
     */
    private void updateUserData(User user, NcuUserDTO ncuUserDTO) {
        user.setEmail(ncuUserDTO.getEmail());
        user.setNameZh(ncuUserDTO.getChineseName());
        user.setStudentId(ncuUserDTO.getStudentId());
        user.setNameEn(ncuUserDTO.getEnglishName());
        if (ncuUserDTO.getGender() != null) {
            user.setGender(Short.parseShort(ncuUserDTO.getGender()));
        }
        user.setBirthday(ncuUserDTO.getBirthday());
        user.setPersonalId(ncuUserDTO.getPersonalId());
        user.setPhone(ncuUserDTO.getMobilePhone());
    }
}
