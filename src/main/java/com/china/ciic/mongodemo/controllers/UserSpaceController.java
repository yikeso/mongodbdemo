package com.china.ciic.mongodemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 用户主页
 */
@Controller
@PreAuthorize("hasAnyRole('admin')")
@RequestMapping("/u")
public class UserSpaceController {

    private static final Logger log = LoggerFactory.getLogger(UserSpaceController.class);

    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username,
                            Model model){
        log.debug("username:{}",username);
        return "userspace/u";
    }

    @RequestMapping("/{username}/blogs")
    public String userBlogList(@PathVariable("username") String username,
                               @RequestParam(value = "order",required = false,defaultValue = "new") String order,
                               @RequestParam(value = "category",required = false) String category,
                               @RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize,
                               Model model){
        log.debug("username:{},order:{},category:{},keyword:{},page:{},pageSize:{}",
                username,order,category,keyword,page,pageSize);
        return "userspace/u";
    }

    @GetMapping("/{username}/blogs/{id}")
    public String userBlog(@PathVariable("id") String id,Model model){
        log.debug("id:{}",id);
        return "userspace/blog";
    }

    @GetMapping("/{username}/blogs/edit")
    public String addBlog(@PathVariable("username") String username,Model model){
        log.debug("{} 创建新博客",username);
        model.addAttribute("author",username);
        return "userspace/blogedit";
    }

    @GetMapping("/{username}/blogs/edit/{id}")
    public String updateBlog(@PathVariable("username") String username,
                             @PathVariable("id") String id,
                             Model model){
        log.debug("{} 修改id为{}博客",username,id);
        return "userspace/blogedit";
    }

    @PostMapping("/{username}/blogs/save")
    public String saveBlog(@PathVariable("username") String username){

        return "redirect:/"+username+"/blogs?order=new";
    }
}
