package com.web.ncumt.service.impl;

import com.web.ncumt.dto.PostFront;
import com.web.ncumt.entity.Post;
import com.web.ncumt.repository.PostRepository;
import com.web.ncumt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostFront> listActivePost() {
        List<Post> posts = postRepository.findByExpiredAtAfterOrderByPinDescCreatedAtDesc(LocalDateTime.now());
        return posts.stream()
                .map(PostFront::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }
}
