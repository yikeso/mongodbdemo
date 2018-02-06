package com.china.ciic.mongodemo.controllers;

import com.china.ciic.mongodemo.common.MyPasswordEncoder;
import com.china.ciic.mongodemo.common.ServerResponse;
import com.china.ciic.mongodemo.mongo.po.User;
import com.china.ciic.mongodemo.mongo.repositories.UserRepository;
import com.china.ciic.mongodemo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/users")
public class UserController {

    @Resource
    MyPasswordEncoder myPasswordEncoder;

    @Resource
    UserRepository userRepository;

    @Resource
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * 返回用户列表
     *
     * @param model
     * @return
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("title","用户管理");
        return "users/list";
    }

    /**
     * 根据id获取用户
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String userDetail(@PathVariable("id") String id,Model model){
        User user = userRepository.findOne(id);
        model.addAttribute("user",user);
        model.addAttribute("title","查看用户");
        return "users/view";
    }

    /**
     * 获取创建用户表单
     * @param model
     * @return
     */
    @GetMapping("/form")
    public String userForm(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("title","创建用户");
        return "users/form";
    }

    /**
     * 创建用户重定向到用户列表
     * @return
     */
    @PostMapping("/form")
    public String userForm(User user,Model model){
        if(StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getEmail())){
            model.addAttribute("user",new User());
            model.addAttribute("title","创建用户");
            model.addAttribute("errorMessage","用户名或邮箱为空!");
            return "users/form";
        }
        if(StringUtils.isEmpty(user.getId())) {
            user.setCreateTime(new Date());
            user = userRepository.insert(user);
            log.debug("创建用户{}",user);
        }else {
            user.setUpdateTime(new Date());
            userService.updateById(user);
            log.debug("修改用户{}",user);
        }
        return "redirect:/users";
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{id}")
    public ServerResponse userDelete(@PathVariable("id") String id){
        userRepository.delete(id);
        return ServerResponse.OK();
    }

    /**
     * 根据id获取用户
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/modify/{id}")
    public String modifyUser(@PathVariable("id") String id,Model model){
        User user = userRepository.findOne(id);
        model.addAttribute("user",user);
        model.addAttribute("title","编辑用户");
        return "users/form";
    }
}
