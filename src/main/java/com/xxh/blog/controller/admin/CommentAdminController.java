package com.xxh.blog.controller.admin;


import com.xxh.blog.service.CommentService;
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
public class CommentAdminController {
    @Autowired
    private CommentService commentService;

    //实现分页列表
    @GetMapping("/comments")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction= Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",commentService.listComments(pageable));
        return "admin/comments";
    }


    //删除分类
    @GetMapping("/comments/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message","删除成功!");
        return "redirect:/admin/comments";
    }
}
