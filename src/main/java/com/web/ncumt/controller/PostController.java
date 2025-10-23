package com.web.ncumt.controller;

import com.web.ncumt.constant.ModelAttributeConstant;
import com.web.ncumt.constant.SessionConstant;
import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.constant.ViewNameConstant;
import com.web.ncumt.dto.LoginUser;
import com.web.ncumt.dto.PostForm;
import com.web.ncumt.dto.PostFront;
import com.web.ncumt.entity.Post;
import com.web.ncumt.helper.ToastMessageHelper;
import com.web.ncumt.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/post")
public class PostController {


    @Autowired
    private ToastMessageHelper toastMessageHelper;

    @Autowired
    private PostService postService;

    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "創建公告");
        if (!model.containsAttribute(ModelAttributeConstant.POST_FORM)) {
            model.addAttribute(ModelAttributeConstant.POST_FORM, new PostForm());
        }
        return ViewNameConstant.CREATE_POST;
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute(ModelAttributeConstant.POST_FORM) PostForm postForm,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "創建公告");
            return ViewNameConstant.CREATE_POST;
        }

        LoginUser loginUser = (LoginUser) session.getAttribute(SessionConstant.CURRENT_LOGIN_USER);

        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setType(postForm.getType());
        post.setPin(postForm.getPin());
        post.setExpiredAt(postForm.getExpiredAt());
        post.setContent(postForm.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setUserId(loginUser.getId().toString());
        post.setCreateUser(loginUser.getId());
        post.setModifyUser(loginUser.getId());

        postService.createPost(post);

        toastMessageHelper.addSuccessMessage(session, "新增公告成功");

        return URLConstant.redirectTo(URLConstant.POST_CREATE);
    }

    @GetMapping("/list")
    public String listPosts(Model model, @PageableDefault(sort = Post.Fields.createdAt, direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "公告列表");
        Page<PostFront> postPage = postService.pageAllPost(pageable);
        model.addAttribute(ModelAttributeConstant.POST_PAGE, postPage);

        return ViewNameConstant.POST_LIST;
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model) {
        Optional<PostFront> postFrontOptional = postService.getPostFrontById(id);
        if (postFrontOptional.isEmpty()) {
            return URLConstant.redirectTo(URLConstant.POST_LIST);
        }

        model.addAttribute(ModelAttributeConstant.POST, postFrontOptional.get());
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "公告詳情");
        return ViewNameConstant.POST_DETAIL;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isEmpty()) {
            return URLConstant.redirectTo(URLConstant.POST_LIST);
        }

        Post post = postOptional.get();
        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setType(post.getType());
        postForm.setPin(post.getPin());
        postForm.setExpiredAt(post.getExpiredAt());
        postForm.setContent(post.getContent());

        model.addAttribute(ModelAttributeConstant.POST_FORM, postForm);
        model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "編輯公告");
        model.addAttribute(ModelAttributeConstant.POST_ID, id);

        return ViewNameConstant.POST_EDIT;
    }

    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable("id") Long id,
                             @Valid @ModelAttribute(ModelAttributeConstant.POST_FORM) PostForm postForm,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(ModelAttributeConstant.PAGE_TITLE, "編輯公告");
            model.addAttribute(ModelAttributeConstant.POST_ID, id);
            return ViewNameConstant.POST_EDIT;
        }

        LoginUser loginUser = (LoginUser) session.getAttribute(SessionConstant.CURRENT_LOGIN_USER);

        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isEmpty()) {
            toastMessageHelper.addErrorMessage(session, "公告不存在");
            log.warn("post id {} note exists", id);
            return URLConstant.redirectTo(URLConstant.HOME);
        }

        Post post = postOptional.get();
        post.setTitle(postForm.getTitle());
        post.setType(postForm.getType());
        post.setPin(postForm.getPin());
        post.setExpiredAt(postForm.getExpiredAt());
        post.setContent(postForm.getContent());
        post.setUpdatedAt(LocalDateTime.now());
        post.setModifyUser(loginUser.getId());

        postService.updatePost(post);

        toastMessageHelper.addSuccessMessage(session, "更新公告成功");

        return URLConstant.redirectTo(URLConstant.POST_EDIT);
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id, HttpSession session) {
        postService.deletePost(id);
        toastMessageHelper.addSuccessMessage(session, "刪除公告成功");
        return URLConstant.redirectTo(URLConstant.POST_LIST);
    }
}
