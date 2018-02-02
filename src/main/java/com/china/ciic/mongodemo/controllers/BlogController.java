package com.china.ciic.mongodemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * blog控制层
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    @GetMapping
    public String listBlogs(@RequestParam(value = "order",required = false,defaultValue = "new") String order,
                            @RequestParam(value = "keyword",required = false) Long keyword){
        log.debug("order:{},tag:{}",order,keyword);
        return "redirect:/index?order=" + order + "&keyword=" + keyword;
    }
}
