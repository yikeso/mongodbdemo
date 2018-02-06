package com.china.ciic.mongodemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户管理控制层
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String listUsers(Model model){
        return "admin/list";
    }
}
