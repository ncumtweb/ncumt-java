package com.web.ncumt.service.impl;

import com.web.ncumt.entity.Post;
import com.web.ncumt.repository.PostRepository;
import com.web.ncumt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> listActivePost()
    {
        return postRepository.findByExpiredAtAfterOrderByPinDescCreatedAtDesc(LocalDateTime.now());
    }
}
