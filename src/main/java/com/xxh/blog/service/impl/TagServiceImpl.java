package com.xxh.blog.service.impl;

import com.xxh.blog.dao.TagRepository;
import com.xxh.blog.exception.NotFoundException;
import com.xxh.blog.po.Tag;
import com.xxh.blog.po.Type;
import com.xxh.blog.service.TagService;
import com.xxh.blog.utils.StringToList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag sava(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Tag> listTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        //先查询是否存在
        Tag t = tagRepository.findById(id).orElse(null);
        if(t == null ) {
            //抛出不存在异常
            throw new NotFoundException("不存在该类型!");
        }
        //将更新后的tag 的值 赋值给存在的tag
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTags(String ids) {
        //转为list
        return tagRepository.findAllById(new StringToList().list(ids));
    }

    @Override
    public List<Tag> listTagsTop(Integer size) {
        //分页数据排序规则
        //根据 博客数量 从大到小排序
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
