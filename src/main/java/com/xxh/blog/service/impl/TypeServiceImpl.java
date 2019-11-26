package com.xxh.blog.service.impl;

import com.xxh.blog.dao.TypeRepository;
import com.xxh.blog.exception.NotFoundException;
import com.xxh.blog.po.Type;
import com.xxh.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Override
    @Transactional
    public Type sava(Type type) {
        return typeRepository.save(type);
    }

    @Override
    @Transactional
    public Type getType(Long id) {
        //不存在就返回null
       return typeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        //分页查询
        return typeRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        //更新前 先查询是否存在
        Type t = typeRepository.findById(id).orElse(null);
        if(t == null ) {
            //抛出不存在异常
            throw new NotFoundException("不存在该类型!");
        }
        //将更新后的type 的值 赋值给存在的type
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Override
    //通过分页数据 限制 发送数据量 并且根据分类含有的博客数量 排序
    public List<Type> listTypeTop(Integer size) {
        //根据 博客数量 从大到小排序
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
