package com.xxh.blog.service;

import com.xxh.blog.po.Tag;
import com.xxh.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    //增加标签
    Tag sava(Tag tag);

    //查询标签
    Tag getTag(Long id);

    //标签分页
    Page<Tag> listTags(Pageable pageable);
    List<Tag> listTags();
    //修改
    Tag updateTag(Long id,Tag tag);

    //删除
    void deleteTag(Long id);

    //通过名称查询标签
    Tag getTagByName(String name);

    List<Tag> listTags(String ids);

    //发送 size个标签
    List<Tag> listTagsTop(Integer size);
}
