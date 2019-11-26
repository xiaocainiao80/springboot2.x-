package com.xxh.blog.dao;

import com.xxh.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

//数据访问层 jpa
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassowrd(String username,String password);
}
