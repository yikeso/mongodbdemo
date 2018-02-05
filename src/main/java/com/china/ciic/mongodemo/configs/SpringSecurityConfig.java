package com.china.ciic.mongodemo.configs;

import com.china.ciic.mongodemo.common.MyPasswordEncoder;
import com.china.ciic.mongodemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * springSecurity配置文件
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    MyPasswordEncoder myPasswordEncoder;

    @Resource
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/js/**","/fonts/**","/index","/register").permitAll()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error").successForwardUrl("/index");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        //user实体类，盐值属性
        ReflectionSaltSource rss = new ReflectionSaltSource();
        rss.setUserPropertyToUse("salt");
        //DAO认证代理
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //添加盐值获取器
        provider.setSaltSource(rss);
        //添加持久层
        provider.setUserDetailsService(userService);
        //添加密码加密器
        provider.setPasswordEncoder(myPasswordEncoder);
        auth.authenticationProvider(provider);
//        auth.userDetailsService(userService).passwordEncoder(myPasswordEncoder);
    }
}
