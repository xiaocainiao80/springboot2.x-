package com.xxh.blog.controller;

import com.xxh.blog.po.Blog;
import com.xxh.blog.service.BlogService;
import com.xxh.blog.service.TagService;
import com.xxh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class indexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    //返回首页 并且发送分页数据
    @GetMapping("/")
    public String index(
            @PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
           Model model
    ) {

        //发送博客分页数据
        model.addAttribute("page",blogService.listBlog(pageable));
        //发送 6 个 分类
        model.addAttribute("types",typeService.listTypeTop(6));
        //发送 多个标签
        model.addAttribute("tags",tagService.listTagsTop(10));
        //发送8个推荐 博客
        model.addAttribute("recommendBlogs",blogService.listBlogTop(8));

        return "index";
    }

    //返回博客详情页
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    //实现全局搜索博客
    @PostMapping("/search")
    public String search(
            @PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
            Model model,@RequestParam String query
    ){
        //发送查找到的分页数据
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        //查询字符串 返回页面
        model.addAttribute("query",query);


        return "search";
    }

    //尾页推荐博客
    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newblogs", blogService.listSize(3));
        return "_fragments::newBlogList";
    }

}
