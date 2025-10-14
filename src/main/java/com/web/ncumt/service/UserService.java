package com.web.ncumt.service;

import com.web.ncumt.controller.data.NcuUserDTO;
import com.web.ncumt.entity.User;

/**
 * 提供使用者相關的業務邏輯。
 */
public interface UserService {
    /**
     * 儲存使用者資訊。
     *
     * @param user 要儲存的使用者物件
     * @return 儲存後的使用者物件
     */
    User saveUser(User user);

    /**
     * 根據 NcuUserDTO 尋找或建立使用者。
     * <p>
     * 如果使用者已存在（根據電子信箱判斷），則更新其資訊；
     * 否則，建立一個新使用者。
     *
     * @param ncuUserDTO 從 OAuth2 登入流程中獲取的使用者資料傳輸物件
     * @return 找到或建立的使用者物件
     */
    User findOrCreateUser(NcuUserDTO ncuUserDTO);
}
