package com.xxh.blog.controller;

import com.xxh.blog.po.Comment;
import com.xxh.blog.po.User;
import com.xxh.blog.service.BlogService;
import com.xxh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    //评论图像
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String  comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));

        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String postComment(Comment comment,
                              HttpSession session){
        //设置comment 和 blog 关系
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        //判断管理员是否登陆
        User user = (User)session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
//            comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar(avatar);
        }
        //保存
        commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }


}
