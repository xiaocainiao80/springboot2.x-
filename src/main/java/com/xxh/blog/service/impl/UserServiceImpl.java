package com.xxh.blog.service.impl;

import com.xxh.blog.dao.UserRepository;
import com.xxh.blog.po.User;
import com.xxh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //业务逻辑层
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUse(String username, String password) {
        User user = userRepository.findByUsernameAndPassowrd(username,password);
        return user;
    }
}
