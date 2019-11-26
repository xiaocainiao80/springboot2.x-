package com.xxh.blog.service;

import com.xxh.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    //增加分类
    Type sava(Type type);

    //查询分类
    Type getType(Long id);

    //分类分页
    Page<Type> listType(Pageable pageable);
    //展示所有分类
    List<Type> listType();

    //修改
    Type updateType(Long id,Type type);

    //删除
    void deleteType(Long id);

    //通过名称查询分类
    Type getTypeByName(String name);

    //根据数量发送分类数据
    List<Type> listTypeTop(Integer size);
}
