package com.xxh.blog.controller.admin;

import com.xxh.blog.po.User;
import com.xxh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller //后台登陆控制器
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping //默认为类前的/admin
    public String loginPage(){
        //进入后台登陆页面
        return  "admin/login";
    }

    //后台登陆验证
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        //传递消息到页面
                        RedirectAttributes attributes){
        User user = userService.checkUse(username,password);
        if(user != null) {
            //页面不传密码
            user.setPassowrd(null);
            //已登录
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误!");
            return "redirect:/admin";
        }

    }

    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
