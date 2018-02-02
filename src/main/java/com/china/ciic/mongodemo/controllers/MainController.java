package com.china.ciic.mongodemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String root(){
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("title","登录页面");
        return "login";
    }

    @GetMapping("/login-error")
    public String loginErrorPage(Model model){
        model.addAttribute("title","登录页面");
        model.addAttribute("logError",true);
        model.addAttribute("errorMsg","用户名或密码错误！");
        return "login";
    }
}
