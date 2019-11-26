package com.xxh.blog.dao;

import com.xxh.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    //通过名称查询分类
    Type findByName(String name);

    //通过分页数据 限制 发送数据量 并且根据分类含有的博客数量 排序
    //自定义查询
    @Query("select t from Type t  ")
    List<Type> findTop(Pageable pageable);
}
