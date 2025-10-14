package com.web.ncumt.controller;

import com.web.ncumt.entity.User;
import com.web.ncumt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    @SuppressWarnings("unused")
    private UserRepository userRepository;

    @GetMapping("/api/users")
    public List<User> userList() {
        return userRepository.findAll();
    }
}
