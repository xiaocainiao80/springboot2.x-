package com.xxh.blog.controller.admin;


import com.xxh.blog.po.Blog;
import com.xxh.blog.po.User;
import com.xxh.blog.service.BlogService;
import com.xxh.blog.service.TagService;
import com.xxh.blog.service.TypeService;
import com.xxh.blog.utils.TagsToIds;
import com.xxh.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {
    private  static final String INPUT = "admin/blogs-input";
    private  static final String LIST = "admin/blogs";
    private  static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog , Model model){
        //第一次进入 发送所有分类数据
        model.addAttribute("types",typeService.listType());
        //发送分页发布的博客
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    //搜索查询 ajax异步
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog , Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        //刷新blogs 的bloglist
        return "admin/blogs :: blogList";
    }

    //跳转到博客新增页面
    @GetMapping("/blogs/input")
    public String blogInput(Model model){
        //初始化标签 和 分类
        model.addAttribute("tags",tagService.listTags());
        model.addAttribute("types",typeService.listType());
        //为了 和edit公用一个页面 传递空对象
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    //新增form数据接收  接收编辑和新增的数据
    @PostMapping("/blogs")
    public String postInput(Blog blog, RedirectAttributes attributes,HttpSession session){
        //设置 blog的发布者user
        blog.setUser((User)session.getAttribute("user"));
        //设置blog所在分类
        blog.setType(typeService.getType(blog.getType().getId()));
        //设置blog标签
        blog.setTags(tagService.listTags(blog.getTagIds()));
        //接收form的Blog数据
        Blog b ;
        //判断是新增 还是修改
        if(blog.getId() == null) {
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if(b == null) {
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功!");
        }
        return REDIRECT_LIST;
    }

    //编辑页面转发
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable Long id, Model model) {
        //初始化标签 和 分类
        model.addAttribute("tags",tagService.listTags());
        model.addAttribute("types",typeService.listType());
        //根据id 找到blog
        Blog blog = blogService.getBlog(id);
        //将已有标签 转换
        blog.setTagIds(new TagsToIds().tagsToIds(blog.getTags()));
        model.addAttribute("blog",blog);
        return INPUT;

    }

    //删除
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功!");
        return REDIRECT_LIST;
    }


}
