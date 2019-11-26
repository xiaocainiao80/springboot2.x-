package com.xxh.blog.controller;

import com.xxh.blog.po.Type;
import com.xxh.blog.service.BlogService;
import com.xxh.blog.service.TypeService;
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
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    //传当前活跃分类id
    public String types( @PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(1000);
        if(id == -1){
            //说明从导航点击过来的 取第一个分类id
            id = types.get(0).getId() ;
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        //根据分类id 查询分类下博客
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("types",types);
        //将id 传回去
        model.addAttribute("activeTypeId",id);

        return "types";
    }
}
