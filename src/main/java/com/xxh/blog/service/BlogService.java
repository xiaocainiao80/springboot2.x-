package com.xxh.blog.service;

import com.xxh.blog.po.Blog;
import com.xxh.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    //因为 有标题 和 分类搜索分页 所以 传Blog对象
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //前端 只需 分页数据 不需要 查询对象
    Page<Blog> listBlog(Pageable pageable);

    //发送 size 大小的博客数据
    List<Blog> listBlogTop(int size);
    List<Blog> listSize(int size);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    //查找条件 全局搜索 博客
    Page<Blog> listBlog(String query,Pageable pageable);

    //前端展示 博客详情 markdown转html
    Blog getAndConvert(Long id);

    //博客按照 年 从大到小排序 查询博客
    Map<String,List<Blog>> findYearBlog();

    Long countBlog();

}
