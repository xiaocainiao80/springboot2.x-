package com.xxh.blog.dao;

import com.xxh.blog.po.Tag;
import com.xxh.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    //通过名称查询分类
    Tag findByName(String name);

    //对tag 进行分页发送
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
