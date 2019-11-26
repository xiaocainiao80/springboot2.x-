package com.xxh.blog.controller.admin;

import com.xxh.blog.po.Tag;
import com.xxh.blog.po.Type;
import com.xxh.blog.service.TagService;
import com.xxh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    //实现分页列表
    @GetMapping("/tags")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction= Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",tagService.listTags(pageable));
        return "admin/tags";
    }

    //分类新增页面
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    //提交分类新增数据 成功返回分类列表页面
    @PostMapping("/tags")
    //重定向 传递消息RedirectAttributes
    // @Valid 检验效验
    //BindingResult 接收效验结果
    public String postTypes(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //重复验证
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null ){
            //重复名称
            //验证结果 自定义错误消息
            result.rejectValue("name","nameError","该标签已存在！");
        }
        if(result.hasErrors()){
            //非空验证有错误 返回新增页面
            return "admin/tags-input";
        }

        Tag t = tagService.sava(tag);
        if(t == null ){
            //没新增成功
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        //重定向 types的get方法
        return "redirect:tags";
    }

    //修改分类页面 与新增分类公用一个页面
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    //修改分类数据提交 验证
    //提交分类新增数据 成功返回分类列表页面
    @PostMapping("/tags/{id}")
    //重定向 传递消息RedirectAttributes
    // @Valid 检验效验
    //BindingResult 接收效验结果
    public String editType(@Valid Tag tag, BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes attributes){
        //重复验证
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null ){
            //重复名称
            //验证结果 自定义错误消息
            result.rejectValue("name","nameError","该标签已存在！");
        }

        if(result.hasErrors()){
            //非空验证有错误 返回新增页面
            return "admin/tags-input";
        }

        //修改分类
        Tag t = tagService.updateTag(id,tag);
        if(t == null ){
            //没新增成功
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        //重定向 types的get方法
        return "redirect:/admin/tags";
    }

    //删除分类
    @GetMapping("/tags/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/tags";
    }
}
