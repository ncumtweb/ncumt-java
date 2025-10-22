package com.web.ncumt.controller;

import com.web.ncumt.constant.ModelAttributeConstant;
import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.constant.ViewNameConstant;
import com.web.ncumt.dto.LoginUser;
import com.web.ncumt.dto.PostForm;
import com.web.ncumt.entity.Post;
import com.web.ncumt.helper.ToastMessageHelper;
import com.web.ncumt.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * 公告相關的控制器。
 */
@Controller
@RequestMapping("/post")
public class PostController {


    /**
     * 用於新增 Toast 提示訊息的輔助類別。
     */
    @Autowired
    private ToastMessageHelper toastMessageHelper;

    @Autowired
    private PostService postService;

    /**
     * 顯示創建公告的表單頁面。
     *
     * @param model Spring MVC 的 Model 物件
     * @return 創建公告的視圖名稱
     */
    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        // 僅在 Model 中不存在 postForm 物件時，才創建一個新的
        // 這可以防止在驗證失敗或成功後重定向回來時，覆蓋掉已有的（可能帶有錯誤訊息或成功後清空的）表單物件
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "創建公告");
        if (!model.containsAttribute(ModelAttributeConstant.POST_FORM)) {
            model.addAttribute(ModelAttributeConstant.POST_FORM, new PostForm());
        }
        return ViewNameConstant.CREATE_POST;
    }

    /**
     * 處理提交的公告數據，並將其儲存到資料庫。
     *
     * @param postForm      綁定表單數據的 DTO
     * @param bindingResult 用於接收驗證結果
     * @param session       HTTP Session 物件
     * @return 成功後重定向到創建頁面，失敗則返回表單頁面
     */
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute(ModelAttributeConstant.POST_FORM) PostForm postForm,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) { // 新增 Model 參數

        // 如果表單驗證失敗，返回創建頁面以顯示錯誤訊息
        if (bindingResult.hasErrors()) {
            model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "創建公告"); // 新增這行以保留頁面標題
            return ViewNameConstant.CREATE_POST;
        }


        LoginUser loginUser = (LoginUser) session.getAttribute(SessionConstant.CURRENT_LOGIN_USER);

        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setType(postForm.getType());
        post.setPin(postForm.getPin());
        post.setExpiredAt(postForm.getExpiredAt());
        post.setContent(postForm.getContent());

        // 設置後端管理的欄位
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        post.setUserId(loginUser.getId().toString());
        post.setCreateUser(loginUser.getId());
        post.setModifyUser(loginUser.getId());

        postService.createPost(post);

        toastMessageHelper.addSuccessMessage(session, "新增公告成功");

        // 重定向回創建頁面
        return URLConstant.redirectTo(URLConstant.POST_CREATE);
    }
}
