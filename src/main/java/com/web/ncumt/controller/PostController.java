package com.web.ncumt.controller;

import com.web.ncumt.entity.Post;
import com.web.ncumt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/api/posts")
    public List<Post> listPost() {
        return postService.listActivePost();
    }
}
