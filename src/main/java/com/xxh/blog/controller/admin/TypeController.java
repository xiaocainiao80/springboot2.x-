package com.xxh.blog.controller.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xxh.blog.po.Type;
import com.xxh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    //实现分页列表
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction= Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    //分类新增页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    //提交分类新增数据 成功返回分类列表页面
    @PostMapping("/types")
    //重定向 传递消息RedirectAttributes
    // @Valid 检验效验
    //BindingResult 接收效验结果
    public String postTypes(@Valid Type type, BindingResult result,RedirectAttributes attributes){
        //重复验证
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null ){
            //重复名称
            //验证结果 自定义错误消息
            result.rejectValue("name","nameError","该分类已存在！");
        }
        if(result.hasErrors()){
            //非空验证有错误 返回新增页面
            return "admin/types-input";
        }

        Type t = typeService.sava(type);
        if(t == null ){
            //没新增成功
            attributes.addFlashAttribute("message","新增失败");
        }else {
            attributes.addFlashAttribute("message","新增成功");
        }
        //重定向 types的get方法
        return "redirect:types";
    }

    //修改分类页面 与新增分类公用一个页面
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    //修改分类数据提交 验证
    //提交分类新增数据 成功返回分类列表页面
    @PostMapping("/types/{id}")
    //重定向 传递消息RedirectAttributes
    // @Valid 检验效验
    //BindingResult 接收效验结果
    public String editType(@Valid Type type, BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes attributes){
        //重复验证
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null ){
            //重复名称
            //验证结果 自定义错误消息
            result.rejectValue("name","nameError","该分类已存在！");
        }

        if(result.hasErrors()){
            //非空验证有错误 返回新增页面
            return "admin/types-input";
        }

        //修改分类
        Type t = typeService.updateType(id,type);
        if(t == null ){
            //没新增成功
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        //重定向 types的get方法
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/types";
    }
}
