package com.xxh.blog.controller;

import com.xxh.blog.po.Tag;
import com.xxh.blog.service.BlogService;
import com.xxh.blog.service.TagService;
import com.xxh.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagsShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagsTop(1000);
        if(id == -1){
            //说明从导航点击过来的 取第一个标签id
            id = tags.get(0).getId() ;
        }
        //根据标签id 查询标签下博客
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("tags",tags);
        //将id 传回去
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
