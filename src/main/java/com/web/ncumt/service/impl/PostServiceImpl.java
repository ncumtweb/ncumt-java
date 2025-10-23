package com.web.ncumt.service.impl;

import com.web.ncumt.dto.PostFront;
import com.web.ncumt.entity.Post;
import com.web.ncumt.entity.User;
import com.web.ncumt.repository.PostRepository;
import com.web.ncumt.repository.UserRepository;
import com.web.ncumt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@SuppressWarnings("unused")
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<PostFront> pageActivePost(Pageable pageable) {
        Page<Post> postPage = postRepository.findByExpiredAtAfter(LocalDateTime.now(), pageable);
        return toPostFrontPage(postPage);
    }

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Page<PostFront> pageAllPost(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return toPostFrontPage(postPage);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<PostFront> getPostFrontById(Long id) {
        return postRepository.findById(id).map(post -> {
            if (post.getCreateUser() == null || post.getModifyUser() == null) {
                return PostFront.fromEntity(post);
            }
            Map<Long, String> userIdToNameMap = userRepository.findAllById(List.of(post.getCreateUser(), post.getModifyUser()))
                    .stream().collect(Collectors.toMap(User::getId, User::getNameZh));

            return PostFront.fromEntity(post, userIdToNameMap);
        });
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    /**
     *
     * @param postPage PostPage
     * @return PostFrontPage
     */
    private Page<PostFront> toPostFrontPage(Page<Post> postPage) {
        List<Post> postList = postPage.getContent();
        Set<Long> userIdSet = postList.stream()
                .flatMap(post -> Stream.of(post.getCreateUser(), post.getModifyUser()))
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, String> userIdToNameMap = userRepository.findAllById(userIdSet).stream()
                .collect(Collectors.toMap(User::getId, User::getNameZh));

        List<PostFront> dtoList = postList.stream()
                .map(post -> PostFront.fromEntity(post, userIdToNameMap))
                .toList();

        return new PageImpl<>(dtoList, postPage.getPageable(), postPage.getTotalElements());
    }
}
