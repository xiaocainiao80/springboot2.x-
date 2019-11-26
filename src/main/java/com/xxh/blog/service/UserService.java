package com.xxh.blog.service;

import com.xxh.blog.po.User;

public interface UserService {

    User checkUse(String username,String password);
}
